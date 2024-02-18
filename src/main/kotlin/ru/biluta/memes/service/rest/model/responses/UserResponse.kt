package ru.biluta.memes.service.rest.model.responses

import io.swagger.v3.oas.annotations.media.Schema

data class UserResponse(

    @Schema(description = "Айди в базе")
    val id: Long,
    @Schema(description = "Айди пользователя")
    val userId: Long,
    @Schema(description = "Логин")
    val username: String?

)