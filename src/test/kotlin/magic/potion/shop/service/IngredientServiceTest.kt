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
    }

    @Test
    fun findIngredientByName() {
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