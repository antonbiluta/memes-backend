package ru.biluta.memes.service.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.biluta.memes.service.persistence.model.ChatSettings

interface ChatSettingsRepository : JpaRepository<ChatSettings, Long> {

    fun getByChatId(chatId: Long): ChatSettings?

    fun getFirstByChatPrefix(chatPrefix: String): ChatSettings?

}