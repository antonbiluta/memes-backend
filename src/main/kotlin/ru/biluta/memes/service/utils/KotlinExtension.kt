package ru.biluta.memes.service.utils

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

object KotlinExtension {

    fun <T> T.notEqualsAndExists(other: T): Boolean {
        return this != null && this != other
    }

    fun Int.basePageable(): Pageable = PageRequest.of(0, this)

}