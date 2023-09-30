package magic.potion.shop.controller

import magic.potion.shop.model.Wizard
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

    @DeleteMapping(value = ["/{id}"])
    fun deleteWizard(@PathVariable(value="id") id: Long) : Wizard {
        return wizardService.delete(id)
    }
}