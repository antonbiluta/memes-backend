package ru.biluta.memes.service.rest.model.exception

data class RequestException(
    val code: String?,
    val description: String?,
    val details: ErrorDetails?
)
