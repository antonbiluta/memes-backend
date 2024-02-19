package ru.biluta.memes.service.mapping

import ru.biluta.memes.service.domain.model.MemInfo
import ru.biluta.memes.service.enums.ContentTypePath
import ru.biluta.memes.service.persistence.model.Memes
import ru.biluta.memes.service.rest.model.requests.MemInfoRequest
import java.time.LocalDateTime
import ru.biluta.memes.service.rest.model.responses.MemInfoResponse as MemInfoApi

object MemMapper {

    fun MemInfoRequest.toDomain(): MemInfo {
        return MemInfo(
            userId = this.userId,
            chatId = this.chatId,
            username = null,
            chatPrefix = null,
            fileOrigin = this.file,
            filePath = null,
            file = this.file.bytes,
            fileType = this.fileType,
            createdAt = LocalDateTime.now()
        )
    }

    fun MemInfo.toData(): Memes {
        return Memes(
            chatId = this.chatId,
            userId = this.userId,
            filePath = this.filePath!!,
            fileType = this.fileType!!.path
        )
    }

    fun Memes.toDomain(): MemInfo {
        return MemInfo(
            chatId = this.chatId,
            userId = this.userId,
            filePath = this.filePath,
            fileType = ContentTypePath.getContentTypeByPath(this.fileType),
            chatPrefix = this.chatSettings?.chatPrefix,
            fileOrigin = null,
            file = null,
            username = this.user?.username,
            createdAt = this.createdAt
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