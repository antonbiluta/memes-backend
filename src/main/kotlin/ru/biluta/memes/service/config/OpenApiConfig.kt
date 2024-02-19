package ru.biluta.memes.service.config

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
                .addServersItem(Server()
                        .url("/")
                        .description("Default Server URL")
                )
                .info(Info()
                        .title("MemesApp")
                        .description("API для работы с мемами")
                )
    }

    @Bean
    fun memesServiceApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("Memes")
            .pathsToMatch("/api/memes/**")
            .build()
    }

    @Bean
    fun userServiceApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
                .group("User")
                .pathsToMatch("/api/users/**")
                .build()
    }

    @Bean
    fun chatServiceApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
                .group("Chat")
                .pathsToMatch("/api/chat/**")
                .build()
    }
}