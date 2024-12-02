package ru.biluta.memes.service.mapping

import ru.biluta.memes.service.domain.model.MediaInfo
import ru.biluta.memes.service.enums.ContentTypePath
import ru.biluta.memes.service.persistence.model.Memes
import ru.biluta.memes.service.rest.model.requests.MemInfoRequest
import java.time.LocalDateTime
import ru.biluta.memes.service.rest.model.responses.MemInfoResponse as MemInfoApi

object MemMapper {

    fun MemInfoRequest.toDomain(): MediaInfo {
        return MediaInfo(
            userId = this.userId,
            chatId = this.chatId,
            user = MediaInfo.User(
                firstName = null,
                lastName = null,
                username = null
            ),
            chatPrefix = null,
            fileOrigin = this.file,
            filePath = null,
            file = this.file.bytes,
            fileType = this.fileType,
            createdAt = LocalDateTime.now()
        )
    }

    fun MediaInfo.toData(): Memes {
        return Memes(
            chatId = this.chatId,
            userId = this.userId,
            filePath = this.filePath!!,
            fileType = this.fileType!!.path
        )
    }

    fun List<Memes>.toDomain(): List<MediaInfo> {
        return map { it.toDomain() }
    }

    fun Memes.toDomain(): MediaInfo {
        return MediaInfo(
            chatId = this.chatId,
            userId = this.userId,
            filePath = this.filePath,
            fileType = ContentTypePath.getContentTypeByPath(this.fileType),
            chatPrefix = this.chatSettings?.chatPrefix,
            fileOrigin = null,
            file = null,
            user = MediaInfo.User(
                firstName = this.user?.firstName,
                lastName = this.user?.lastName,
                username = this.user?.username
            ),
            createdAt = this.createdAt
        )
    }

    fun MediaInfo.toResponse(): MemInfoApi {
        return MemInfoApi(
            userId = this.userId,
            chatId = this.chatId,
            userLink = this.getLink(),
            username = this.user.getPrimaryName(),
            chatPrefix = this.chatPrefix,
            fileByteArray = this.file,
            filePath = this.filePath,
            fileType = this.fileType
        )
    }

}