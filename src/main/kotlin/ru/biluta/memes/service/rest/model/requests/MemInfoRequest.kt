package ru.biluta.memes.service.rest.model.requests

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.multipart.MultipartFile
import ru.biluta.memes.service.enums.ContentTypePath

data class MemInfoRequest(

    @Schema(description = "айди пользователя")
    val userId: Long,
    @Schema(description = "айди чата")
    val chatId: Long,
    @Schema(description = "файл")
    val file: MultipartFile,
    @Schema(description = "тип файла")
    val fileType: ContentTypePath

)