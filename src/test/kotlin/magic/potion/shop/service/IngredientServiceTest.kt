package magic.potion.shop.service

import junit.framework.TestCase.assertEquals
import magic.potion.shop.model.Ingredient
import magic.potion.shop.model.IngredientFlavor
import magic.potion.shop.repositories.IngredientRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class IngredientServiceTest {


   @Mock
   private lateinit var ingredientRepository: IngredientRepository

   @InjectMocks
   private lateinit var ingredientService: IngredientService

   @BeforeEach
   fun setUp(){
      MockitoAnnotations.openMocks(this);
   }

    @Test
    fun findAll() {
    }

    @Test
    fun findById() {
    }

    @Test
    fun findIngredientByName() {
    }

    @Test
    fun createIngredient() {
       var testIngredient = Ingredient(0, "Dance Apple", IngredientFlavor.SWEET)

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