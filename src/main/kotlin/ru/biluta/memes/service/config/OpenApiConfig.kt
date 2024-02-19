package ru.biluta.memes.service.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig{

    private val ADMIN_AUTH_KEY = "adminBasic"
    private val userPathsPrefix = "/api/users"

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
                .info(Info().title("MemesApp").description("API для работы с мемами").version("0.1-Beta"))
                .addServersItem(Server().url("/").description("Default Server URL"))
                .components(Components()
                        .addSecuritySchemes(ADMIN_AUTH_KEY, adminSecurityScheme())
                )
    }

    @Bean
    fun memesServiceApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("Memes-Service")
            .pathsToMatch("/api/**")
            .addOpenApiCustomizer(openApiCustomiser)
            .build()
    }

    private fun adminSecurityScheme(): SecurityScheme {
        return SecurityScheme().type(SecurityScheme.Type.HTTP)
                .`in`(SecurityScheme.In.HEADER)
                .name("Admin Basic")
                .scheme("basic")
    }

    private val openApiCustomiser = object : OpenApiCustomizer {
        override fun customise(openApi: OpenAPI) {
            val authKeysPathPrefix = mapOf(
                    "$userPathsPrefix/**" to ADMIN_AUTH_KEY
            )
            openApi.paths.forEach { path ->
                authKeysPathPrefix.entries.forEach eachEntry@{ entry ->
                    val prefix = entry.key
                    val authKey = entry.value
                    if (path.key.startsWith(prefix)) {
                        path.value.readOperations().forEach { operation ->
                            operation.security = listOf(SecurityRequirement().addList(authKey))
                        }
                        return@eachEntry
                    }
                }
            }
        }
    }
}