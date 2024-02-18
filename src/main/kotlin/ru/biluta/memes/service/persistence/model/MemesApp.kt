package ru.biluta.memes.service.persistence.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime


@Entity
@Table(name="memes_app")
class MemesApp (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var chatId: Long,
    var userId: Long,
    var author: String,
    var media: ByteArray,
    var chatPrefix: String,
    var mediaType: String,
    var createdAt: LocalDateTime = LocalDateTime.now()

)