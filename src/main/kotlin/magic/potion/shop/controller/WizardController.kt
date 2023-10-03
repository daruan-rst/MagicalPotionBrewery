package magic.potion.shop.controller

import magic.potion.shop.model.Wizard
import magic.potion.shop.request.PotionIngredientRequest
import magic.potion.shop.request.RecipeIngredientRequest
import magic.potion.shop.service.WizardService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/wizard/v1")
class WizardController(private val wizardService: WizardService) {

    @GetMapping
    fun findAll(): List<Wizard>{
        return wizardService.findAll()
    }

    @GetMapping(value = ["/{id}"])
    fun findById(@PathVariable(value="id") id: Long) : Wizard {
        return wizardService.findById(id)
    }

    @PostMapping
    fun createWizard(@RequestBody wizard: Wizard) : Wizard{
        return wizardService.createWizard(wizard)
    }

    @PutMapping(value = ["/{id}"])
    fun update(@RequestBody wizard: Wizard, @PathVariable(value="id") id: Long) : Wizard {
        return wizardService.updateWizard(id, wizard)
    }

    @PatchMapping(value = ["ingredients/{id}"])
    fun addPotionIngredients(@RequestBody potionIngredients: List<PotionIngredientRequest>, @PathVariable(value="id") id: Long): Wizard {
        return wizardService.addPotionIngredients(id, potionIngredients)
    }

    @PatchMapping(value = ["recipe/{id}"])
    fun craftRecipe(@RequestBody recipeRequest: RecipeIngredientRequest, @PathVariable(value="id") id: Long): Wizard {
        return wizardService.craftARecipe(id, recipeRequest)
    }

    @PatchMapping(value = ["potion/{id}/{recipeId}"])
    fun brewAPotion(@PathVariable(value="id") id: Long,@PathVariable(value="id") recipeId: Long ): Wizard {
        return wizardService.brewAPotion(id, recipeId)
    }


    @PatchMapping(value = ["bottle/{id}"])
    fun findABottle(@PathVariable(value="id") id: Long): Wizard {
        return wizardService.findABottle(id)
    }

    @DeleteMapping(value = ["/{id}"])
    fun deleteWizard(@PathVariable(value="id") id: Long) : Wizard {
        return wizardService.delete(id)
    }
}