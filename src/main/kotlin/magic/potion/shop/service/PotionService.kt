package magic.potion.shop.service

import magic.potion.shop.controller.PotionController
import magic.potion.shop.exceptions.RequiredObjectIsNullException
import magic.potion.shop.exceptions.ResourceNotFoundException
import magic.potion.shop.model.Potion
import magic.potion.shop.repositories.PotionRepository
import org.jboss.logging.Logger
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Service

@Service
class PotionService(
    private val potionRepository: PotionRepository,
    private val logger: Logger = Logger.getLogger(PotionService::class.java.name)
) {

    fun findAll(): List<Potion> {
        logger.info("Finding all Potion")
        val potions = potionRepository.findAll()
        potions.forEach {
            it.add(WebMvcLinkBuilder.linkTo(PotionController::class.java).slash(it.id).withSelfRel())
        }

        return potions
    }

    fun findById(id: Long): Potion {
        logger.info("Searching a Potion!")
        val potion = potionRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No potion found for this Id") }
        potion.removeLinks()
        val withSelfRel = WebMvcLinkBuilder.linkTo(PotionController::class.java).slash(potion.id).withSelfRel()
        potion.add(withSelfRel)
        return potion
    }

    fun createPotion(potion: Potion?): Potion {
        potion?: throw RequiredObjectIsNullException()
        logger.info("Creating a Potion!")
        val createdPotion = potionRepository.save(potion)
        val withSelfRel =
            WebMvcLinkBuilder.linkTo(PotionController::class.java).slash(createdPotion.id).withSelfRel()
        createdPotion.add(withSelfRel)
        return createdPotion
    }

    fun updatePotion(id: Long, potion: Potion): Potion {
        val potionThatWillBeUpdated: Potion = findById(id)

        logger.info("Updating a Potion")
        potionThatWillBeUpdated.power = potion.power
        potionThatWillBeUpdated.effect = potion.effect
        val withSelfRel =
            WebMvcLinkBuilder.linkTo(PotionController::class.java).slash(potionThatWillBeUpdated.id)
                .withSelfRel()
        potionThatWillBeUpdated.add(withSelfRel)
        return potionRepository.save(potionThatWillBeUpdated)
    }

    fun delete(id: Long): Potion {
        val potion = findById(id)
        logger.info("Deleting a potion")
        potionRepository.deleteById(id)
        return potion
    }


}