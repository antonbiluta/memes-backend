package ru.biluta.memes.service.mapping

import ru.biluta.memes.service.domain.model.ChatInfo
import ru.biluta.memes.service.persistence.model.ChatSettings
import ru.biluta.memes.service.rest.model.responses.ChatResponse

object ChatMapper {

    fun ChatSettings.toDomain(): ChatInfo {
        return ChatInfo(
            id = this.id,
            chatId = this.chatId,
            chatPrefix = this.chatPrefix
        )
    }

    fun ChatInfo.toApi(): ChatResponse {
        return ChatResponse(
            id = this.id,
            chatId = this.chatId,
            chatPrefix = this.chatPrefix
        )
    }

}