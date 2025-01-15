package ru.biluta.memes.service.domain.core.delegate

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import ru.biluta.memes.service.domain.core.enricher.MediaFilePathEnricher
import ru.biluta.memes.service.domain.model.MediaInfo
import ru.biluta.memes.service.mapping.MemMapper.toDomain
import ru.biluta.memes.service.persistence.repository.MemesRepository
import ru.biluta.memes.service.rest.model.exception.NotFoundException
import ru.biluta.memes.service.utils.constants.Exceptions.CODE_MEMES_NOT_FOUND
import ru.biluta.memes.service.utils.constants.Exceptions.MEMES_NOT_FOUND

@Service
class MediaDelegate(
    val enricher: MediaFilePathEnricher,
    val repository: MemesRepository
) {

    fun findMemes(
        chatPrefix: String,
        userId: Long?,
        limit: Int?
    ): List<MediaInfo> {
        val pagination = PageRequest.of(ZERO, limit ?: NUMBER_FIVE)
        val results = when (userId != null) {
            true -> repository.findMemesByChatPrefixAndUserId(chatPrefix, userId, pagination)
            else -> repository.findMemesByChatPrefix(chatPrefix, pagination)
        } ?: throw NotFoundException(CODE_MEMES_NOT_FOUND, MEMES_NOT_FOUND)
        return results.toDomain()
    }

    companion object {
        const val ZERO = 0
        const val NUMBER_FIVE = 5
    }
}