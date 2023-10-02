package magic.potion.shop.service

import magic.potion.shop.controller.IngredientController
import magic.potion.shop.controller.WizardController
import magic.potion.shop.exceptions.RequiredObjectIsNullException
import magic.potion.shop.exceptions.ResourceNotFoundException
import magic.potion.shop.model.PotionIngredient
import magic.potion.shop.model.Wizard
import magic.potion.shop.repositories.PotionIngredientRepository
import magic.potion.shop.repositories.PotionRepository
import magic.potion.shop.repositories.WizardRepository
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class WizardService(
    private val wizardRepository: WizardRepository,
    private val potionIngredientRepository: PotionIngredientRepository,
    private val logger: Logger = Logger.getLogger(WizardService::class.java.name)) {

    fun findAll(): List<Wizard> {
        logger.info("Finding all Wizards")
        val wizards = wizardRepository.findAll()
        for (wizard in wizards){
            val withSelfRel = linkTo(WizardController::class.java).slash(wizard.id).withSelfRel()
            wizard.add(withSelfRel)
        }

        return wizards;
    }

    fun createWizard(wizard: Wizard?): Wizard{
        if (wizard == null){
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
            .orElseThrow{ ResourceNotFoundException("No records found for this Id") }
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
        wizardThatWillBeUpdated.potions = wizard.potions
        val withSelfRel = WebMvcLinkBuilder.linkTo(IngredientController::class.java).slash(wizardThatWillBeUpdated.id).withSelfRel()
        wizardThatWillBeUpdated.add(withSelfRel)
        return wizardRepository.save(wizardThatWillBeUpdated)
    }

    fun addPotionIngredients(id: Long, ingredients: List<PotionIngredient>) : Wizard{
        val foundWizard = findById(id)
        ingredients.forEach { ingredient -> ingredient.wizard = foundWizard }
        potionIngredientRepository.saveAll(ingredients)
        foundWizard.ingredientInventory.plus(ingredients)
        wizardRepository.save(foundWizard)
        return foundWizard

    }

    fun delete(id: Long): Wizard {
        val wizard = findById(id)
        logger.info("Deleting a wizard")
        wizardRepository.deleteById(id)
        return wizard
    }
}