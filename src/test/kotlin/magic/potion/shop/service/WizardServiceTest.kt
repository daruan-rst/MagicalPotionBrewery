package magic.potion.shop.service

import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import magic.potion.shop.exceptions.ResourceNotFoundException
import magic.potion.shop.model.Wizard
import magic.potion.shop.repositories.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.testcontainers.shaded.com.google.common.collect.ImmutableList
import org.testcontainers.shaded.com.google.common.collect.ImmutableSet
import java.util.*

class WizardServiceTest {

    @Mock
    private lateinit var wizardRepository: WizardRepository

    @Mock
    private lateinit var ingredientService: IngredientService

    @Mock
    private lateinit var recipeIngredientRepository: RecipeIngredientRepository

    @Mock
    private lateinit var recipeRepository: RecipeRepository

    @Mock
    private lateinit var potionRepository: PotionRepository

    private lateinit var wizardService: WizardService

    var testWizard = Wizard(1,"Antonio Carlos Jobim",
                                        ImmutableSet.of(),
                                        ImmutableSet.of(),
                                        ImmutableSet.of())

    var testWizard2 = Wizard(2,"Chet Baker",
            ImmutableSet.of(),
            ImmutableSet.of(),
            ImmutableSet.of())

    @BeforeEach
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        wizardService = WizardService(wizardRepository, ingredientService, recipeIngredientRepository, recipeRepository, potionRepository)
    }

    @Test
    fun findAll() {

        Mockito.`when`(wizardRepository.findAll()).thenReturn(ImmutableList.of(testWizard, testWizard2))

        var allWizards = wizardService.findAll()

        assertEquals(allWizards.size, 2)
        assertEquals(allWizards.first.name, "Antonio Carlos Jobim")
        assertEquals(allWizards.last.name, "Chet Baker")
    }

    @Test
    fun findById() {

        Mockito.`when`(wizardRepository.findById(1)).thenReturn(Optional.of(testWizard))

        var wizard = wizardService.findById(1)

        assertEquals(testWizard.id, 1)
        assertEquals(testWizard.name, "Antonio Carlos Jobim")
    }

    @Test
    fun `findById should throw ResourceNotFoundException when not found`() {
        Mockito.`when`(wizardRepository.findById(1L)).thenReturn(Optional.empty())

        assertThrows<ResourceNotFoundException> {
            wizardService.findById(1L)
        }
        Mockito.verify(wizardRepository, Mockito.times(1)).findById(1L)
    }
    
    @Test
    fun createWizard(){
        Mockito.`when`(wizardRepository.save(Mockito.any())).thenReturn(testWizard)
        
        var wizard = wizardService.createWizard(testWizard)

        assertEquals(wizard.id, testWizard.id)
        assertEquals(wizard.name, testWizard.name)
        Mockito.verify(wizardRepository, Mockito.times(1)).save(testWizard)
        TestCase.assertNotNull(wizard.links)
        TestCase.assertTrue(wizard.links.hasLink("self"))
    }


    
    
}