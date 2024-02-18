package ru.biluta.memes.service.rest.model.responses

data class ChatResponse(

    val id: Long,
    val chatId: Long,
    val chatPrefix: String?

)