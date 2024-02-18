package ru.biluta.memes.service.domain.model

import org.springframework.web.multipart.MultipartFile
import ru.biluta.memes.service.enums.ContentTypePath
import java.time.LocalDateTime

data class MemInfo(

    val userId: Long,
    val chatId: Long,
    val username: String?,
    val chatPrefix: String?,
    val fileOrigin: MultipartFile?,
    val file: ByteArray?,
    var filePath: String?,
    val fileType: ContentTypePath?,
    val createdAt: LocalDateTime

)