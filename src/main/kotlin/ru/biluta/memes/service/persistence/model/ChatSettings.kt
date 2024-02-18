package ru.biluta.memes.service.persistence.model

import jakarta.persistence.*

@Entity
@Table(name="chat_settings")
class ChatSettings(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "chat_id", nullable = false, unique = true)
    val chatId: Long,

    @Column(name = "chat_prefix")
    var chatPrefix: String? = null,

    @OneToMany(mappedBy = "chatSettings", fetch = FetchType.LAZY)
    val memes: List<Memes> = mutableListOf()

)