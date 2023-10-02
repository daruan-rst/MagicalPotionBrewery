package magic.potion.shop.service

import magic.potion.shop.repositories.PotionRepository
import org.jboss.logging.Logger
import org.springframework.stereotype.Service

@Service
class PotionService {

    private lateinit var potionRepository: PotionRepository

    private val logger = Logger.getLogger(PotionService::class.java.name)




}