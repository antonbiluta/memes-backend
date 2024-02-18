package ru.biluta.memes.service.domain

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.biluta.memes.service.domain.model.ChatInfo
import ru.biluta.memes.service.mapping.ChatMapper.toDomain
import ru.biluta.memes.service.persistence.model.ChatSettings
import ru.biluta.memes.service.persistence.repository.ChatSettingsRepository
import ru.biluta.memes.service.rest.model.exception.NotFoundException
import ru.biluta.memes.service.rest.model.requests.ChatEditRequest
import ru.biluta.memes.service.utils.StringExtension.isLong
import ru.biluta.memes.service.utils.constants.Exceptions.CHAT_NOT_FOUND
import ru.biluta.memes.service.utils.constants.Exceptions.CODE_CHAT_NOT_FOUND

@Service
class ChatSettingsService(
    private val repository: ChatSettingsRepository
) {

    @Transactional
    fun getChatSettingsByChat(
        chat: String
    ): ChatInfo {
        return when (chat.isLong()) {
            true -> repository.getByChatId(chat.toLong())
            else -> repository.getFirstByChatPrefix(chat)
        }?.toDomain()
            ?: throw NotFoundException(CODE_CHAT_NOT_FOUND, CHAT_NOT_FOUND)
    }

    fun updatePrefixById(
        chatId: Long,
        request: ChatEditRequest
    ): ChatInfo {
        val chat =  repository.getByChatId(chatId)?.let {
            val wasChanged = it.updateChatFromRequest(request)
            it.saveIfNeed(wasChanged)
        } ?: repository.save(request.createData(chatId))
        return chat.toDomain()
    }

    private fun ChatSettings.saveIfNeed(needed: Boolean): ChatSettings {
        return when (needed) {
            true -> repository.save(this)
            else -> this
        }
    }

    private fun ChatEditRequest.createData(chatId: Long): ChatSettings {
        return ChatSettings(
            chatId = chatId,
            chatPrefix = this.chatPrefix
        )
    }

    private fun ChatSettings.updateChatFromRequest(
        request: ChatEditRequest
    ): Boolean {
        var flag = false
        this.apply {
            if (request.chatPrefix.notEqualsAndExists(this.chatPrefix)) {
                this.chatPrefix = request.chatPrefix
                flag = true
            }
        }
        return flag
    }

    private fun <T> T.notEqualsAndExists(other: T): Boolean {
        return this != null && this != other
    }

}