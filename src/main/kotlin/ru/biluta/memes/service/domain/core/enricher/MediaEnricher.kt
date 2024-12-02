package ru.biluta.memes.service.domain.core.enricher

import ru.biluta.memes.service.domain.model.MediaInfo

interface MediaEnricher {

    fun enrich(mediaInfo: MediaInfo): MediaInfo

}