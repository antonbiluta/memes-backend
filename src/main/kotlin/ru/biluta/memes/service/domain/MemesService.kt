package ru.biluta.memes.service.domain

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.biluta.memes.service.persistence.model.MemesApp
import ru.biluta.memes.service.persistence.repository.MemesRepository

@Service
class MemesService(
    private val memesRepository: MemesRepository
) {

    @Transactional(readOnly = true)
    fun findMemesByChatPrefixWithLimit(
            chatPrefix: String,
            limit: Int
    ): List<MemesApp> {
        val pageable = PageRequest.of(0, limit, Sort.by("createdAt").descending())
        val result = memesRepository.findByChatPrefixOrderByCreatedAtDesc(chatPrefix, pageable)
        return result
    }

}