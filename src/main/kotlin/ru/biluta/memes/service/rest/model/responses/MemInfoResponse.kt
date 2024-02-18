package ru.biluta.memes.service.rest.model.responses

import io.swagger.v3.oas.annotations.media.Schema
import ru.biluta.memes.service.enums.ContentTypePath

data class MemInfoResponse(

    @Schema(description = "айди пользователя")
    val userId: Long,
    @Schema(description = "айди чата")
    val chatId: Long,
    @Schema(description = "логин пользователя")
    val username: String?,
    @Schema(description = "префикс чата")
    val chatPrefix: String?,
    @Schema(description = "файл")
    val fileByteArray: ByteArray?,
    @Schema(description = "путь к файлу в хранилище")
    val filePath: String?,
    @Schema(description = "тип файла")
    val fileType: ContentTypePath?

)
