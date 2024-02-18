package ru.biluta.memes.service.mapping

import ru.biluta.memes.service.domain.model.MemInfo
import ru.biluta.memes.service.enums.ContentTypePath
import ru.biluta.memes.service.persistence.model.MemesApp
import ru.biluta.memes.service.rest.model.requests.MemInfoRequest
import java.time.LocalDateTime
import ru.biluta.memes.service.rest.model.responses.MemInfoResponse as MemInfoApi

object MemMapper {

    fun MemInfoRequest.toDomain(): MemInfo {
        return MemInfo(
            userId = this.userId.toLong(),
            chatId = this.chatId.toLong(),
            username = this.username,
            chatPrefix = this.chatPrefix,
            fileOrigin = this.file,
            filePath = null,
            file = this.file.bytes,
            fileType = this.fileType,
            createdAt = LocalDateTime.now()
        )
    }

    fun MemInfo.toData(): MemesApp {
        return MemesApp(
            chatId = this.chatId,
            userId = this.userId,
            author = this.username!!,
            media = this.file!!,
            chatPrefix = this.chatPrefix!!,
            mediaType = this.fileType.toString(),
            createdAt = this.createdAt
        )
    }

    fun MemesApp.toDomain(): MemInfo {
        return MemInfo(
            userId = this.userId,
            chatId = this.chatId,
            username = this.author,
            chatPrefix = this.chatPrefix,
            file = this.media,
            fileType = ContentTypePath.valueOf(this.mediaType),
            createdAt = this.createdAt,
            filePath = null,
            fileOrigin = null
        )
    }

    fun MemInfo.toApi(): MemInfoApi {
        return MemInfoApi(
            userId = this.userId,
            chatId = this.chatId,
            username = this.username,
            chatPrefix = this.chatPrefix,
            fileByteArray = this.file,
            filePath = this.filePath,
            fileType = this.fileType
        )
    }

}