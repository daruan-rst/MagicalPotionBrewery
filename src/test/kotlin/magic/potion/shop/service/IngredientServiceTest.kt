package magic.potion.shop.service

import junit.framework.TestCase.assertEquals
import magic.potion.shop.model.Ingredient
import magic.potion.shop.model.IngredientFlavor
import magic.potion.shop.repositories.IngredientRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`
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
    }

    @Test
    fun createIngredient() {

       `when`(ingredientRepository.save(any<Ingredient>())).thenReturn(testIngredient)

       var ingredient =  ingredientService.createIngredient(testIngredient)

       assertEquals(ingredient.id, testIngredient.id)
       assertEquals(ingredient.name, testIngredient.name)
       assertEquals(ingredient.flavor, testIngredient.flavor)
    }

    @Test
    fun updateIngredient() {
    }

    @Test
    fun delete() {
    }


}