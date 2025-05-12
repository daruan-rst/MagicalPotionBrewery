package magic.potion.shop.service

import junit.framework.TestCase.*
import magic.potion.shop.exceptions.RequiredObjectIsNullException
import magic.potion.shop.exceptions.ResourceNotFoundException
import magic.potion.shop.model.Ingredient
import magic.potion.shop.model.IngredientFlavor
import magic.potion.shop.repositories.IngredientRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

class IngredientServiceTest {

    @Mock
    private lateinit var ingredientRepository: IngredientRepository

    private lateinit var ingredientService: IngredientService

   var testIngredient = Ingredient(0, "Dance Apple", IngredientFlavor.SWEET)

    var testIngredient2 = Ingredient(0, "Sweet Wasabi", IngredientFlavor.SPICY)


    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        ingredientService = IngredientService(ingredientRepository)
    }


    @Test
    fun findAll() {
        var ingredientList: List<Ingredient> = listOf(testIngredient, testIngredient2)

        `when`(ingredientRepository.findAll()).thenReturn(ingredientList);

        var allIngredient: List<Ingredient> = ingredientService.findAll();

        assertEquals(allIngredient.size, 2)
        assertEquals(allIngredient.first, testIngredient)
        assertEquals(allIngredient.last, testIngredient2)

    }

    @Test
    fun findById() {
        testIngredient.id = 0

        `when`(ingredientRepository.findById(0)).thenReturn(Optional.of(testIngredient));

        var ingredient: Ingredient = ingredientService.findById(0);

        assertEquals(ingredient, testIngredient)
    }

    @Test
    fun findIngredientByName() {

        `when`(ingredientRepository.findIngredientByName("Dance Apple")).thenReturn(Optional.of(testIngredient));

        var ingredient: Ingredient = ingredientService.findIngredientByName("Dance Apple");

        assertEquals(ingredient, testIngredient)
        verify(ingredientRepository, times(1)).findIngredientByName("Dance Apple")
        assertNotNull(ingredient.links)
        assertTrue(ingredient.links.hasLink("self"))
    }

    @Test
    fun `findIngredientByName should throw ResourceNotFoundException when not found`() {
        `when`(ingredientRepository.findIngredientByName("Electric Mango")).thenReturn(Optional.empty())

        assertThrows<ResourceNotFoundException> {
            ingredientService.findIngredientByName("Electric Mango")
        }
        verify(ingredientRepository, times(1)).findIngredientByName("Electric Mango")
    }

    @Test
    fun createIngredient() {

       `when`(ingredientRepository.save(any<Ingredient>())).thenReturn(testIngredient)

       var ingredient =  ingredientService.createIngredient(testIngredient)

       assertEquals(ingredient.id, testIngredient.id)
       assertEquals(ingredient.name, testIngredient.name)
       assertEquals(ingredient.flavor, testIngredient.flavor)
        verify(ingredientRepository, times(1)).save(testIngredient)
        assertNotNull(ingredient.links)
        assertTrue(ingredient.links.hasLink("self"))
    }

    @Test
    fun `createIngredient should throw RequiredObjectIsNullException when ingredient is null`() {
        assertThrows<RequiredObjectIsNullException> {
            ingredientService.createIngredient(null)
        }
        verify(ingredientRepository, never()).save(any())
    }

    @Test
    fun updateIngredient() {

        val updatedIngredient = testIngredient.copy(name = "Purple Banana", flavor = IngredientFlavor.SOUR)
        `when`(ingredientRepository.findById(1L)).thenReturn(Optional.of(testIngredient))
        `when`(ingredientRepository.save(testIngredient)).thenReturn(testIngredient)

        val result = ingredientService.updateIngredient(1L, updatedIngredient)

        assertEquals("Purple Banana", result.name)
        assertEquals(IngredientFlavor.SOUR, result.flavor)
        verify(ingredientRepository, times(1)).findById(1L)
        verify(ingredientRepository, times(1)).save(testIngredient)
        assertNotNull(result.links)
        assertTrue(result.links.hasLink("self"))
    }

    @Test
    fun `updateIngredient should throw ResourceNotFoundException when ingredient not found`() {

        `when`(ingredientRepository.findById(1L)).thenReturn(Optional.empty())

        assertThrows<ResourceNotFoundException> {
            ingredientService.updateIngredient(1L, testIngredient)
        }
        verify(ingredientRepository, times(1)).findById(1L)
        verify(ingredientRepository, never()).save(any())
    }


    @Test
    fun `delete should return deleted ingredient and call repository delete`() {
        `when`(ingredientRepository.findById(1L)).thenReturn(Optional.of(testIngredient))

        val result = ingredientService.delete(1L)

        assertEquals(testIngredient, result)
        verify(ingredientRepository, times(1)).findById(1L)
        verify(ingredientRepository, times(1)).deleteById(1L)
    }


}