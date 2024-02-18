package ru.biluta.memes.service.persistence.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="memes")
class Memes(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "user_id", nullable = false)
    var chatId: Long,

    @Column(name = "chat_id", nullable = false)
    var userId: Long,

    @Column(name = "file_path", nullable = false)
    var filePath: String,

    @Column(name = "file_type", nullable = false)
    var fileType: String,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", referencedColumnName = "chat_id", insertable = false, updatable = false)
    val chatSettings: ChatSettings? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    val user: User? = null

)