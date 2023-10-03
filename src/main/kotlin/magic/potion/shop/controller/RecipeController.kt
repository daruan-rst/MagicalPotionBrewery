package magic.potion.shop.controller

import magic.potion.shop.model.Recipe
import magic.potion.shop.service.RecipeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/ingredient/v1")
class RecipeController(private val recipeService: RecipeService) {

    @GetMapping
    fun findAll(): List<Recipe>{
        return recipeService.findAll()
    }

    @GetMapping(value = ["/{id}"])
    fun findById(@PathVariable(value="id") id: Long) : Recipe {
        return recipeService.findById(id)
    }

    @GetMapping(value = ["findByIngredientName/{name}"])
    fun findByName(@PathVariable(value="name") name: String) : List<Recipe> {
        return recipeService.findRecipeByIngredient(name)
    }

    @PostMapping
    fun createRecipe(@RequestBody ingredient: Recipe) : Recipe {
        return recipeService.createRecipe(ingredient)
    }

    @PutMapping(value = ["/{id}"])
    fun updateRecipe(@RequestBody ingredient: Recipe, @PathVariable(value="id") id: Long) : Recipe {
        return recipeService.updateRecipe(id, ingredient)
    }

    @DeleteMapping(value = ["/{id}"])
    fun deleteRecipe(@PathVariable(value="id") id: Long) : Recipe{
        return recipeService.delete(id)
    }
}