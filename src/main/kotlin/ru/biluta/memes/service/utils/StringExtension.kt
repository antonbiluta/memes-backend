package ru.biluta.memes.service.utils

object StringExtension {

    fun String.isLong(): Boolean {
        try {
            toLong()
            return true
        } catch(ex: Exception) {
            return false
        }
    }

}