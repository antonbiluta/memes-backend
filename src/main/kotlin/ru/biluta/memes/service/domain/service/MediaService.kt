package ru.biluta.memes.service.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.biluta.memes.service.domain.core.delegate.MediaDelegate
import ru.biluta.memes.service.domain.model.MediaInfo
import ru.biluta.memes.service.mapping.MemMapper.toData
import ru.biluta.memes.service.mapping.MemMapper.toDomain

@Service
class MediaService(
    private val mediaDelegate: MediaDelegate
) {

    @Transactional(readOnly = true)
    fun findMemesByChatPrefixWithLimit(
        chatPrefix: String,
        userId: Long?,
        limit: Int?
    ): List<MediaInfo> {
        return mediaDelegate.findMemes(chatPrefix, userId, limit)
    }

    @Transactional
    fun saveMeme(mediaInfo: MediaInfo): MediaInfo {
        mediaDelegate.enricher.enrich(mediaInfo)
        return mediaDelegate.repository.save(mediaInfo.toData()).toDomain()
    }

}