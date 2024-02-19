package ru.biluta.memes.service.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.core.jackson.ModelResolver
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.customizers.OperationCustomizer
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.AnnotationUtils
import ru.biluta.memes.service.config.security.annotations.AllowOnlyAdmin
import ru.biluta.memes.service.utils.constants.Security.ADMIN_AUTH_KEY

@Configuration
class OpenApiConfig{

    @Bean
    fun modelResolver(objectMapper: ObjectMapper): ModelResolver = ModelResolver(objectMapper)

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
                .info(Info()
                        .title("MemesApp")
                        .description("API для работы с мемами")
                        .version("0.1-Beta"))
                .addServersItem(Server()
                        .url("/")
                        .description("This server Memes-API"))
                .components(Components()
                        .addSecuritySchemes(ADMIN_AUTH_KEY, adminSecurityScheme())
                )
    }

    @Bean
    fun memesServiceApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("Memes-Service")
            .pathsToMatch("/api/**")
            .addOperationCustomizer(secureOperationCustomizer())
            .build()
    }

    fun secureOperationCustomizer(): OperationCustomizer {
        return OperationCustomizer { operation, handlerMethod ->
            if (AnnotationUtils.findAnnotation(handlerMethod.method, AllowOnlyAdmin::class.java) != null) {
                operation.addSecurityItem(SecurityRequirement().addList("adminBasic"))
            }
            operation
        }
    }

    private fun adminSecurityScheme(): SecurityScheme {
        return SecurityScheme().type(SecurityScheme.Type.HTTP)
                .`in`(SecurityScheme.In.HEADER)
                .name("admin")
                .scheme("basic")
    }
}