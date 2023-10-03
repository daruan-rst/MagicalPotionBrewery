package magic.potion.shop.service

import magic.potion.shop.controller.IngredientController
import magic.potion.shop.controller.RecipeController
import magic.potion.shop.exceptions.RequiredObjectIsNullException
import magic.potion.shop.exceptions.ResourceNotFoundException
import magic.potion.shop.model.*
import magic.potion.shop.repositories.RecipeRepository
import org.jboss.logging.Logger
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Service

@Service
class RecipeService(
    private val recipeRepository: RecipeRepository,
    private val logger: Logger = Logger.getLogger(RecipeService::class.java.name)
) {

    fun findAll(): List<Recipe> {
        logger.info("Finding all Recipes")
        val recipes = recipeRepository.findAll()
        for (recipe in recipes) {
            val withSelfRel = WebMvcLinkBuilder.linkTo(RecipeController::class.java).slash(recipe.id).withSelfRel()
            recipe.add(withSelfRel)
        }

        return recipes
    }

    fun findRecipeByIngredient(ingredientName: String): List<Recipe> {
        logger.info(String.format("Searching an Ingredient by the name of %s", ingredientName))
        val recipeList = recipeRepository.findByIngredientsIngredientName(ingredientName)
        if (recipeList.isEmpty()) {
            throw ResourceNotFoundException("No recipe was found for ingredient $ingredientName")
        }
        recipeList.forEach { recipe ->
            val withSelfRel =
                WebMvcLinkBuilder.linkTo(RecipeController::class.java).slash(recipe.id).withSelfRel()
            recipe.add(withSelfRel)
        }
        return recipeList
    }

    fun findById(id: Long): Recipe {
        logger.info("Searching an Recipe!")
        val recipe = recipeRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this Id") }
        recipe.removeLinks()
        val withSelfRel = WebMvcLinkBuilder.linkTo(IngredientController::class.java).slash(recipe.id).withSelfRel()
        recipe.add(withSelfRel)
        return recipe
    }

    fun createRecipe(recipe: Recipe?): Recipe {
        if (recipe == null) {
            throw RequiredObjectIsNullException()
        }
        logger.info("Creating a Recipe!")
        val createdRecipe = recipeRepository.save(recipe)
        val withSelfRel = WebMvcLinkBuilder.linkTo(RecipeController::class.java).slash(createdRecipe.id).withSelfRel()
        createdRecipe.add(withSelfRel)
        return createdRecipe
    }


    fun updateRecipe(id: Long, recipe: Recipe): Recipe {
        val recipeThatWillBeUpdated: Recipe = findById(id)

        logger.info("Updating a Recipe")
        recipeThatWillBeUpdated.name = recipe.name
        recipeThatWillBeUpdated.description = recipe.description
        recipeThatWillBeUpdated.ingredients = recipe.ingredients
        val withSelfRel =
            WebMvcLinkBuilder.linkTo(IngredientController::class.java).slash(recipeThatWillBeUpdated.id).withSelfRel()
        recipeThatWillBeUpdated.add(withSelfRel)
        return recipeRepository.save(recipeThatWillBeUpdated)
    }


    fun delete(id: Long): Recipe {
        val recipe = findById(id)
        logger.info("Deleting a recipe")
        recipeRepository.deleteById(id)
        return recipe
    }
}