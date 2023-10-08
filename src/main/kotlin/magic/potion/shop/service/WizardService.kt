package magic.potion.shop.service

import magic.potion.shop.controller.IngredientController
import magic.potion.shop.controller.WizardController
import magic.potion.shop.exceptions.NotEnoughResourcesException
import magic.potion.shop.exceptions.RequiredObjectIsNullException
import magic.potion.shop.exceptions.ResourceNotFoundException
import magic.potion.shop.model.*
import magic.potion.shop.repositories.PotionRepository
import magic.potion.shop.repositories.RecipeIngredientRepository
import magic.potion.shop.repositories.RecipeRepository
import magic.potion.shop.repositories.WizardRepository
import magic.potion.shop.request.PotionIngredientRequest
import magic.potion.shop.request.RecipeIngredientRequest
import magic.potion.shop.utils.validatePotionIngredient
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.logging.Logger

@Service
class WizardService(
    private val wizardRepository: WizardRepository,
    private val ingredientService: IngredientService,
    private val recipeIngredientRepository: RecipeIngredientRepository,
    private val recipeRepository: RecipeRepository,
    private val potionRepository: PotionRepository,
    private val logger: Logger = Logger.getLogger(WizardService::class.java.name)
) {

    fun findAll(): List<Wizard> {
        logger.info("Finding all Wizards")
        val wizards = wizardRepository.findAll()
        for (wizard in wizards) {
            val withSelfRel = linkTo(WizardController::class.java).slash(wizard.id).withSelfRel()
            wizard.add(withSelfRel)
        }

        return wizards
    }

    fun createWizard(wizard: Wizard?): Wizard {
        if (wizard == null) {
            throw RequiredObjectIsNullException()
        }
        logger.info("Creating a Wizard!")
        val createdWizard = wizardRepository.save(wizard)
        val withSelfRel = linkTo(WizardController::class.java).slash(createdWizard.id).withSelfRel()
        createdWizard.add(withSelfRel)
        return createdWizard
    }

    fun findById(id: Long): Wizard {
        logger.info("Searching an Wizard!")
        val wizard = wizardRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this Id") }
        wizard.removeLinks()
        val withSelfRel = linkTo(IngredientController::class.java).slash(wizard.id).withSelfRel()
        wizard.add(withSelfRel)
        return wizard
    }

    fun updateWizard(id: Long, wizard: Wizard): Wizard {
        val wizardThatWillBeUpdated: Wizard = findById(id)

        logger.info("Updating a Wizard")
        wizardThatWillBeUpdated.name = wizard.name
        wizardThatWillBeUpdated.bottleInventory = wizard.bottleInventory
        wizardThatWillBeUpdated.ingredientInventory = wizard.ingredientInventory
        wizardThatWillBeUpdated.recipes = wizard.recipes
        val withSelfRel = linkTo(IngredientController::class.java).slash(wizardThatWillBeUpdated.id).withSelfRel()
        wizardThatWillBeUpdated.add(withSelfRel)
        return wizardRepository.save(wizardThatWillBeUpdated)
    }

    fun addPotionIngredients(id: Long, potionIngredientRequests: List<PotionIngredientRequest>): Wizard {
        val foundWizard = findById(id)

        potionIngredientRequests.forEach { ingredientRequest ->
            val ingredient: Ingredient = getOrCreateIngredient(ingredientRequest)

            val existingPotionIngredient = foundWizard.ingredientInventory.find { it.wizardIngredient == ingredient }

            if (existingPotionIngredient != null) {
                existingPotionIngredient.quantity += ingredientRequest.quantity
            } else {
                val newPotionIngredient = PotionIngredient(0, foundWizard, ingredient, ingredientRequest.quantity)
                foundWizard.ingredientInventory += newPotionIngredient
            }
        }

        wizardRepository.save(foundWizard)
        return foundWizard
    }

    fun craftARecipe(id: Long, recipeIngredientRequest: RecipeIngredientRequest): Wizard{
        val foundWizard = findById(id)
        val recipe = Recipe(
            name = recipeIngredientRequest.name,
            description = recipeIngredientRequest.description
        )
        recipeIngredientRequest.potionIngredientList.forEach { ingredientRequest ->
            validatePotionIngredient(ingredientRequest)

            val ingredient = getOrCreateIngredient(ingredientRequest)

            val recipeIngredient = recipeIngredientRepository.findRecipeIngredientByIngredientAndQuantity(ingredient, ingredientRequest.quantity)
                .orElseGet{ recipeIngredientRepository.save(RecipeIngredient(0,ingredient, ingredientRequest.quantity))}

            recipe.ingredients += recipeIngredient

        }
        foundWizard.recipes += recipe
        wizardRepository.save(foundWizard)
        return foundWizard
    }

    fun brewAPotion(wizardId:Long, recipeId: Long) : Wizard{
        val wizard = findById(wizardId)
        val thisRecipe = recipeRepository.findById(recipeId)
            .orElseThrow { ResourceNotFoundException("No recipe found for this Id") }
        val emptyBottle: Bottle =
            wizard.bottleInventory.find { it.volume == 0 }
                ?: throw NotEnoughResourcesException("No empty bottle available for the wizard.")

        thisRecipe.ingredients.forEach { recipeIngredient ->
            val wizardIngredient = wizard.ingredientInventory.find {
                it.wizardIngredient.name == recipeIngredient.recipeIngredient.name
            } ?: throw NotEnoughResourcesException("Wizard does not have required ingredient: ${recipeIngredient.recipeIngredient.name}")

            if (wizardIngredient.quantity < recipeIngredient.quantity) {
                throw NotEnoughResourcesException("Not enough quantity of ingredient: ${recipeIngredient.recipeIngredient.name}")
            }

            wizardIngredient.quantity -= recipeIngredient.quantity

            if (wizardIngredient.quantity.compareTo(BigDecimal.ZERO) == 0) {
                wizard.ingredientInventory -= wizardIngredient
            }
        }



        // Create a new Potion object and save it to the database
        val potion = Potion(0, thisRecipe, calculateEffect(thisRecipe), calculatePower(thisRecipe))
        val savedPotion = potionRepository.save(potion)

        // Set the saved potion to the empty bottle
        emptyBottle.potion = savedPotion
        emptyBottle.volume = 100

        wizard.bottleInventory += emptyBottle
        wizardRepository.save(wizard)

        return wizard
    }

    private fun calculateEffect(recipe: Recipe): PotionEffect {
        val effectCounter = mutableMapOf(
            PotionEffect.STAMINA to 0,
            PotionEffect.SPEED to 0,
            PotionEffect.HEALING to 0,
            PotionEffect.INVISIBILITY to 0,
            PotionEffect.STRENGTH to 0
        )

        recipe.ingredients.forEach { ri ->
            val ingredient = ri.recipeIngredient
            when (ingredient.flavor) {
                IngredientFlavor.SOUR -> effectCounter[PotionEffect.STAMINA] = effectCounter.getOrDefault(PotionEffect.STAMINA, 0) + 1
                IngredientFlavor.SPICY -> effectCounter[PotionEffect.SPEED] = effectCounter.getOrDefault(PotionEffect.SPEED, 0) + 1
                IngredientFlavor.SWEET -> effectCounter[PotionEffect.HEALING] = effectCounter.getOrDefault(PotionEffect.HEALING, 0) + 1
                IngredientFlavor.BITTER -> effectCounter[PotionEffect.INVISIBILITY] = effectCounter.getOrDefault(PotionEffect.INVISIBILITY, 0) + 1
                IngredientFlavor.SALTY -> effectCounter[PotionEffect.STRENGTH] = effectCounter.getOrDefault(PotionEffect.STRENGTH, 0) + 1
                IngredientFlavor.FLAVORLESS -> {
                    val randomEffect = PotionEffect.values().random()
                    effectCounter[randomEffect] = effectCounter.getOrDefault(randomEffect, 0) + 1
                }
            }
        }

        return effectCounter.maxByOrNull { it.value }?.key ?: PotionEffect.STAMINA
    }


    private fun calculatePower(recipe: Recipe) : Long{
        val randomFactor = Math.random() * 10 - 5
        val charArray = recipe.description.toCharArray()

        val sum = charArray.sumOf { it.code } + randomFactor.toInt()

        return (sum + 100).toLong()
    }

    private fun getOrCreateIngredient(ingredientRequest: PotionIngredientRequest): Ingredient {
        validatePotionIngredient(ingredientRequest)
        val ingredient : Ingredient = try {
            ingredientService.findIngredientByName(ingredientRequest.ingredientName)

        }catch ( e: ResourceNotFoundException){
            ingredientService.createIngredient(Ingredient(0, ingredientRequest.ingredientName, ingredientRequest.flavor))
        }
        return ingredient
    }

    fun findABottle(id: Long): Wizard {
        val wizard = findById(id)
        logger.info("The wizard ${wizard.name} has found an empty bottle")
        val bottle = Bottle(0,wizard,null,0)
        wizard.bottleInventory += bottle
        wizardRepository.save(wizard)
        return wizard
    }

    fun delete(id: Long): Wizard {
        val wizard = findById(id)
        logger.info("Deleting a wizard")
        wizardRepository.deleteById(id)
        return wizard
    }
}