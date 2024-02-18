package ru.biluta.memes.service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class MemesBackendApplication

fun main(args: Array<String>) {
    runApplication<MemesBackendApplication>(*args)
}
