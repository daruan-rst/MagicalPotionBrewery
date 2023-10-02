package magic.potion.shop.service

import magic.potion.shop.controller.IngredientController
import magic.potion.shop.exceptions.RequiredObjectIsNullException
import magic.potion.shop.exceptions.ResourceNotFoundException
import magic.potion.shop.model.Ingredient
import magic.potion.shop.repositories.IngredientRepository
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class IngredientService(
    private val ingredientRepository: IngredientRepository,
    private val logger: Logger = Logger.getLogger(IngredientService::class.java.name)
) {

    fun findAll(): List<Ingredient> {
        logger.info("Finding all Ingredients")
        val ingredients = ingredientRepository.findAll()
        for (ingredient in ingredients) {
            val withSelfRel =
                WebMvcLinkBuilder.linkTo(IngredientController::class.java).slash(ingredient.id).withSelfRel()
            ingredient.add(withSelfRel)
        }

        return ingredients
    }

    fun findById(id: Long): Ingredient {
        logger.info("Searching an Ingredient!")
        val ingredient = ingredientRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this Id") }
        ingredient.removeLinks()
        val withSelfRel = WebMvcLinkBuilder.linkTo(IngredientController::class.java).slash(ingredient.id).withSelfRel()
        ingredient.add(withSelfRel)
        return ingredient
    }

    fun findIngredientByName(name : String): Ingredient {
        logger.info(String.format("Searching an Ingredient by the name of %s", name))
        val ingredient = ingredientRepository.findIngredientByName(name)
            .orElseThrow { ResourceNotFoundException(String.format("No records found for the name of %s", name)) }
        ingredient.removeLinks()
        val withSelfRel = WebMvcLinkBuilder.linkTo(IngredientController::class.java).slash(ingredient.id).withSelfRel()
        ingredient.add(withSelfRel)
        return  ingredient

    }

    fun createIngredient(ingredient: Ingredient?): Ingredient {
        if (ingredient == null) {
            throw RequiredObjectIsNullException()
        }
        logger.info("Creating a Ingredient!")
        val createdIngredient = ingredientRepository.save(ingredient)
        val withSelfRel =
            WebMvcLinkBuilder.linkTo(IngredientController::class.java).slash(createdIngredient.id).withSelfRel()
        createdIngredient.add(withSelfRel)
        return createdIngredient
    }

    fun updateIngredient(id: Long, ingredient: Ingredient): Ingredient {
        val ingredientThatWillBeUpdated: Ingredient = findById(id)

        logger.info("Updating an Ingredient")
        ingredientThatWillBeUpdated.flavor = ingredient.flavor
        ingredientThatWillBeUpdated.name = ingredient.name
        val withSelfRel =
            WebMvcLinkBuilder.linkTo(IngredientController::class.java).slash(ingredientThatWillBeUpdated.id)
                .withSelfRel()
        ingredientThatWillBeUpdated.add(withSelfRel)
        return ingredientRepository.save(ingredientThatWillBeUpdated)
    }

    fun delete(id: Long): Ingredient {
        val ingredient = findById(id)
        logger.info("Deleting an ingredient")
        ingredientRepository.deleteById(id)
        return ingredient
    }
}