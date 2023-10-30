package magic.potion.shop.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import magic.potion.shop.model.PotionIngredient
import magic.potion.shop.model.Wizard
import magic.potion.shop.request.RecipeIngredientRequest
import magic.potion.shop.service.WizardService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/api/wizard")
@Tag(name = "Wizard", description = "Endpoints for Managing People")
class WizardController(private val wizardService: WizardService) {

    @GetMapping
    @Operation(summary = "Finds all Wizards", description = "Finds all Wizards",
        tags = ["Wizard"],
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(array = ArraySchema(schema = Schema(implementation = Wizard::class)))
                ]
            ),
            ApiResponse(description = "Bad Request", responseCode = "400", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Internal Error", responseCode = "500", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
        ]
    )
    fun findAll(): List<Wizard>{
        return wizardService.findAll()
    }

    @GetMapping(value = ["/{id}"])
    @Operation(summary = "Finds a Wizard", description = "Finds a Wizard",
        tags = ["Wizard"],
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(array = ArraySchema(schema = Schema(implementation = Wizard::class)))
                ]
            ),
            ApiResponse(description = "Bad Request", responseCode = "400", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Internal Error", responseCode = "500", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
        ]
    )
    fun findById(@PathVariable(value="id") id: Long) : Wizard {
        return wizardService.findById(id)
    }

    @PostMapping
    @Operation(summary = "Creates a new Wizard", description = "Creates a new Wizard",
        tags = ["Wizard"],
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(schema = Schema(implementation = Wizard::class))
                ]
            ),
            ApiResponse(description = "Bad Request", responseCode = "400", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Not Found", responseCode = "404", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Internal Error", responseCode = "500", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
        ]
    )
    fun createWizard(@RequestBody wizard: Wizard) : Wizard{
        return wizardService.createWizard(wizard)
    }

    @PutMapping(value = ["/{id}"])
    @Operation(summary = "Updates a Wizard", description = "Updates a new Wizard",
        tags = ["Wizard"],
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(schema = Schema(implementation = Wizard::class))
                ]
            ),
            ApiResponse(description = "Bad Request", responseCode = "400", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Not Found", responseCode = "404", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Internal Error", responseCode = "500", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
        ]
    )
    fun update(@RequestBody wizard: Wizard, @PathVariable(value="id") id: Long) : Wizard {
        return wizardService.updateWizard(id, wizard)
    }

    @PatchMapping(value = ["/{id}/ingredients"])
    @Operation(summary = "Updates a Wizard's inventory", description = "Adds ingredients to a Wizards inventory",
        tags = ["Wizard"],
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(schema = Schema(implementation = Wizard::class))
                ]
            ),
            ApiResponse(description = "Bad Request - will be thrown when passing a negative value for quantity", responseCode = "400", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Not Found", responseCode = "404", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Internal Error", responseCode = "500", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
        ]
    )
    fun addPotionIngredients(@RequestBody potionIngredients: List<PotionIngredient>, @PathVariable(value="id") id: Long): Wizard {
        return wizardService.addPotionIngredients(id, potionIngredients)
    }

    @PatchMapping(value = ["/{id}/recipe"])
    @Operation(summary = "Patching a recipe to a Wizard", description = "When a wizard creates a recipe",
        tags = ["Wizard"],
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(schema = Schema(implementation = Wizard::class))
                ]
            ),
            ApiResponse(description = "Bad Request  - will be thrown when passing a negative value for quantity", responseCode = "400", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Not Found", responseCode = "404", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Internal Error", responseCode = "500", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
        ]
    )
    fun craftRecipe(@RequestBody recipeRequest: RecipeIngredientRequest, @PathVariable(value="id") id: Long): Wizard {
        return wizardService.craftARecipe(id, recipeRequest)
    }

    @PatchMapping(value = ["/{id}/potion/{recipeId}"])
    @Operation(summary = "Patches a potion to one of the Wizard's empty bottle", description = "Given a known recipe, " +
            "if the wizard has enough resources - bottle or ingredients - a potion can be brewed",
        tags = ["Wizard"],
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(schema = Schema(implementation = Wizard::class))
                ]
            ),
            ApiResponse(description = "Bad Request", responseCode = "400", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Not Found", responseCode = "404", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),ApiResponse(description = "Unprocessable Entity -  will be thrown when there aren't enough resources", responseCode = "422", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Internal Error", responseCode = "500", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
        ]
    )
    fun brewAPotion(@PathVariable(value="id") id: Long,@PathVariable(value="id") recipeId: Long ): Wizard {
        return wizardService.brewAPotion(id, recipeId)
    }


    @PatchMapping(value = ["{id}/bottle"])
    @Operation(summary = "Patches a bottle to a Wizard", description = "An empty bottle will be needed to brew a potion. This endpoint adds an empty bottle to a wizard",
        tags = ["Wizard"],
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(schema = Schema(implementation = Wizard::class))
                ]
            ),
            ApiResponse(description = "Bad Request", responseCode = "400", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Internal Error", responseCode = "500", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
        ]
    )
    fun findABottle(@PathVariable(value="id") id: Long): Wizard {
        return wizardService.findABottle(id)
    }

    @DeleteMapping(value = ["/{id}"])
    @Operation(summary = "Deletes a Wizard", description = "Deletes a Wizard",
        tags = ["Wizard"],
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(array = ArraySchema(schema = Schema(implementation = Wizard::class)))
                ]
            ),
            ApiResponse(description = "Bad Request", responseCode = "400", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Internal Error", responseCode = "500", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
        ]
    )
    fun deleteWizard(@PathVariable(value="id") id: Long) : Wizard {
        return wizardService.delete(id)
    }
}