package ru.biluta.memes.service.rest.model.requests

import io.swagger.v3.oas.annotations.media.Schema

data class ChatEditRequest(

    @Schema(description = "Администратор чата")
    val chatAdmin: String?,
    @Schema(description = "Список модераторов чата")
    val chatModers: List<Int>?,
    @Schema(description = "Префикс чата")
    val chatPrefix: String?,
    val somethingParam: String?

)