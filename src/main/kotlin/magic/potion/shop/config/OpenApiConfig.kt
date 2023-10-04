package magic.potion.shop.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig  {

    @Bean
    fun customOpenApi(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("This is our Potion Shop!")
                    .version("v1")
                    .description("Feel free to craft your own potions")
                    .termsOfService("https://some.wizardry/terms-of-service")
                    .license(
                        License().name("Wizardry ")
                            .url("https://some.wizardry/license")
                    )
            )
    }
}