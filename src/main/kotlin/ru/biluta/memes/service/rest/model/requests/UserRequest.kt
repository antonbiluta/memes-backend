package ru.biluta.memes.service.rest.model.requests

import io.swagger.v3.oas.annotations.media.Schema

data class UserRequest(

        @Schema(description = "Айди пользователя в телеграмм")
        val userId: Long,
        @Schema(description = "Логин пользователя телеграмм")
        val username: String?,
        @Schema(description = "Имя пользователя")
        val firstName: String?,
        @Schema(description = "Фамилия пользователя")
        val lastName: String?

)