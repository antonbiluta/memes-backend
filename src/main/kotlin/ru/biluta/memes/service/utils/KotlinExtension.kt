package ru.biluta.memes.service.utils

object KotlinExtension {

    fun <T> T.notEqualsAndExists(other: T): Boolean {
        return this != null && this != other
    }

}