package ru.biluta.memes.service.domain.service

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.biluta.memes.service.domain.model.MemInfo
import ru.biluta.memes.service.mapping.MemMapper.toData
import ru.biluta.memes.service.mapping.MemMapper.toDomain
import ru.biluta.memes.service.persistence.repository.MemesRepository

@Service
class MemesService(
    private val memesRepository: MemesRepository,
    private val minioService: MinioService
) {

    @Transactional(readOnly = true)
    fun findMemesByChatPrefixWithLimit(
        chatPrefix: String,
        limit: Int
    ): List<MemInfo>? {
        val pageable = PageRequest.of(0, limit, Sort.by("createdAt").descending())
        val result = memesRepository.findMemesByChatPrefix(chatPrefix, pageable)
        return result?.map { it.toDomain() }
    }

    @Transactional
    fun saveMeme(
        memInfo: MemInfo
    ): MemInfo {
        minioService.uploadFile(memInfo).also {
            memInfo.apply { this.filePath = it }
        }
        val meme = memesRepository.save(memInfo.toData())
        return meme.toDomain()
    }

}