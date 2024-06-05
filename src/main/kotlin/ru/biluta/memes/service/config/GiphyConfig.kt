package ru.biluta.memes.service.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class GiphyConfig {

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}