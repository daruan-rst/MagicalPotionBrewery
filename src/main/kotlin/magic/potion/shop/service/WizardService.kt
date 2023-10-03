package magic.potion.shop.service

import magic.potion.shop.controller.IngredientController
import magic.potion.shop.controller.WizardController
import magic.potion.shop.exceptions.RequiredObjectIsNullException
import magic.potion.shop.exceptions.ResourceNotFoundException
import magic.potion.shop.model.Bottle
import magic.potion.shop.model.Ingredient
import magic.potion.shop.model.PotionIngredient
import magic.potion.shop.model.Wizard
import magic.potion.shop.repositories.BottleRepository
import magic.potion.shop.repositories.IngredientRepository
import magic.potion.shop.repositories.PotionIngredientRepository
import magic.potion.shop.repositories.WizardRepository
import magic.potion.shop.request.PotionIngredientRequest
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class WizardService(
    private val wizardRepository: WizardRepository,
    private val potionIngredientRepository: PotionIngredientRepository,
    private val ingredientRepository: IngredientRepository,
    private val bottleRepository: BottleRepository,
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
        val withSelfRel = WebMvcLinkBuilder.linkTo(IngredientController::class.java).slash(wizard.id).withSelfRel()
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
        val withSelfRel = WebMvcLinkBuilder.linkTo(IngredientController::class.java).slash(wizardThatWillBeUpdated.id).withSelfRel()
        wizardThatWillBeUpdated.add(withSelfRel)
        return wizardRepository.save(wizardThatWillBeUpdated)
    }

    fun addPotionIngredients(id: Long, potionIngredientRequests: List<PotionIngredientRequest>): Wizard {
        val foundWizard = findById(id)
        val ingredientList = foundWizard.ingredientInventory.map { inn->inn.wizardIngredient  }.toList()
//        val potionIngredients: MutableList<PotionIngredient> = mutableListOf()
        potionIngredientRequests.forEach { ingredientRequest ->
            val ingredientOptional = ingredientRepository.findIngredientByName(ingredientRequest.ingredientName)
            val ingredient: Ingredient = if (ingredientOptional.isEmpty) {
                ingredientRepository.save(Ingredient(0, ingredientRequest.ingredientName, ingredientRequest.flavor))
            } else {
                ingredientOptional.get()
            }

            val potionIngredient: PotionIngredient =
                PotionIngredient(0, foundWizard, ingredient, ingredientRequest.quantity)


            if (ingredientList.contains(ingredient)){
                foundWizard.ingredientInventory.forEach { pi ->
                    if (pi.wizardIngredient == ingredient){
                        potionIngredient.quantity += pi.quantity
                        foundWizard.ingredientInventory -= pi
                    }
                }
            }
            foundWizard.ingredientInventory += potionIngredient
            }

        wizardRepository.save(foundWizard)
        return foundWizard

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