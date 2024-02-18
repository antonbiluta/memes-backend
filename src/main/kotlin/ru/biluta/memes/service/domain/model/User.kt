package ru.biluta.memes.service.domain.model

data class User(

    val id: Long,
    val userId: Long,
    val username: String?,
    val firstName: String?,
    val lastName: String?

)
