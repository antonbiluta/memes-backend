package ru.biluta.memes.service.persistence.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import ru.biluta.memes.service.persistence.model.MemesApp

interface MemesAppRepository : JpaRepository<MemesApp, Long> {

    fun findByChatPrefixOrderByCreatedAtDesc(chatPrefix: String, pageable: Pageable): List<MemesApp>

    fun findByChatIdOrderByCreatedAtDesc(chatId: Long, pageable: Pageable): List<MemesApp>

}