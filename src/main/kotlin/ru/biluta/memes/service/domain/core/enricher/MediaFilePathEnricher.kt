package ru.biluta.memes.service.domain.core.enricher

import org.springframework.stereotype.Component
import ru.biluta.memes.service.domain.model.MediaInfo
import ru.biluta.memes.service.domain.service.MinioService

@Component
class MediaFilePathEnricher(
    private val minioService: MinioService
) : MediaEnricher {

    override fun enrich(mediaInfo: MediaInfo): MediaInfo {
        val filePath = uploadAndGetFilePath(mediaInfo)
        mediaInfo.filePath = filePath
        return mediaInfo
    }

    private fun uploadAndGetFilePath(mediaInfo: MediaInfo): String {
        return minioService.uploadFile(mediaInfo)
    }

}