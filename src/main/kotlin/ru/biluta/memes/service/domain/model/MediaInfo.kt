package ru.biluta.memes.service.domain.model

import org.springframework.web.multipart.MultipartFile
import ru.biluta.memes.service.enums.ContentTypePath
import java.time.LocalDateTime

data class MediaInfo(

    val userId: Long,
    val chatId: Long,
    val user: User,
    val chatPrefix: String?,
    val fileOrigin: MultipartFile?,
    val file: ByteArray?,
    var filePath: String?,
    val fileType: ContentTypePath?,
    val createdAt: LocalDateTime

) {
    data class User(
        val firstName: String?,
        val lastName: String?,
        val username: String?
    ) {
        fun getFullName(): String {
            return "$firstName $lastName"
        }

        fun getPrimaryName(): String? {
            return when (firstName != null || lastName != null) {
                true -> getFullName()
                else -> username
            }
        }
    }

    fun getLink(): String {
        return when (user.username != null) {
            true -> "https://t.me/${user.username}"
            else -> "tg://user?id=$userId"
        }
    }
}