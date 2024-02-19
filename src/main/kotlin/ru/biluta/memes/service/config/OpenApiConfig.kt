import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig{

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
                .info(Info()
                        .title("MemesApp")
                        .description("API для работы с мемами")
                )
                .addServersItem(Server()
                        .url("/")
                        .description("Default Server URL")
                )
    }

    @Bean
    fun memesServiceApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("memes-service")
            .pathsToMatch("/api/memes/**")
            .build()
    }
}