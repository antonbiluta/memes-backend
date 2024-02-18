package ru.biluta.memes.service.persistence.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.biluta.memes.service.persistence.model.Memes

interface MemesRepository : JpaRepository<Memes, Long> {

    fun findMemesByChatId(chatId: Long, pageable: Pageable): List<Memes>?

    @Query("SELECT m FROM Memes m JOIN m.chatSettings cs WHERE cs.chatPrefix = :chatPrefix ORDER BY m.createdAt DESC")
    fun findMemesByChatPrefix(@Param("chatPrefix") chatPrefix: String, pageable: Pageable): List<Memes>?

}