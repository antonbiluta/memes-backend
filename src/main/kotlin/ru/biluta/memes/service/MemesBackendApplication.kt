package ru.biluta.memes.service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MemesBackendApplication

fun main(args: Array<String>) {
    runApplication<MemesBackendApplication>(*args)
}
