package magic.potion.shop.controller

import magic.potion.shop.model.Potion
import magic.potion.shop.service.PotionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/potion/v1")
class PotionController(private val potionService: PotionService) {

    @GetMapping
    fun findAll(): List<Potion>{
        return potionService.findAll()
    }

    @GetMapping(value = ["/{id}"])
    fun findById(@PathVariable(value="id") id: Long) : Potion {
        return potionService.findById(id)
    }

    @PostMapping
    fun createPotion(@RequestBody potion: Potion) : Potion {
        return potionService.createPotion(potion)
    }

    @PutMapping(value = ["/{id}"])
    fun updatePotion(@RequestBody potion: Potion, @PathVariable(value="id") id: Long) : Potion {
        return potionService.updatePotion(id, potion)
    }

    @DeleteMapping(value = ["/{id}"])
    fun deletePotion(@PathVariable(value="id") id: Long) : Potion{
        return potionService.delete(id)
    }
}