package magic.potion.shop.controller

import magic.potion.shop.model.Ingredient
import magic.potion.shop.service.IngredientService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/api/ingredient")
class IngredientController(private val ingredientService: IngredientService) {

    @GetMapping
    fun findAll(): List<Ingredient>{
        return ingredientService.findAll()
    }

    @GetMapping(value = ["/{id}"])
    fun findById(@PathVariable(value="id") id: Long) : Ingredient {
        return ingredientService.findById(id)
    }

    @GetMapping(value = ["findIngredientByName/{name}"])
    fun findByName(@PathVariable(value="name") name: String) : Ingredient {
        return ingredientService.findIngredientByName(name)
    }

    @PostMapping
    fun createIngredient(@RequestBody ingredient: Ingredient) : Ingredient {
        return ingredientService.createIngredient(ingredient)
    }

    @PutMapping(value = ["/{id}"])
    fun updateIngredient(@RequestBody ingredient: Ingredient, @PathVariable(value="id") id: Long) : Ingredient {
        return ingredientService.updateIngredient(id, ingredient)
    }

    @DeleteMapping(value = ["/{id}"])
    fun deleteIngredient(@PathVariable(value="id") id: Long) : Ingredient{
        return ingredientService.delete(id)
    }
}