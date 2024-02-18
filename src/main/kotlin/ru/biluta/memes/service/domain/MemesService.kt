package ru.biluta.memes.service.domain

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.biluta.memes.service.domain.model.MemInfo
import ru.biluta.memes.service.enums.ContentTypePath
import ru.biluta.memes.service.persistence.model.Memes
import ru.biluta.memes.service.persistence.repository.MemesAppRepository
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

    private fun MemInfo.toData(): Memes {
        return Memes(
            chatId = this.chatId,
            userId = this.userId,
            filePath = this.filePath!!,
            fileType = this.fileOrigin?.contentType!!
        )
    }

    private fun Memes.toDomain(): MemInfo {
        return MemInfo(
            chatId = this.chatId,
            userId = this.userId,
            filePath = this.filePath,
            fileType = ContentTypePath.getContentTypeByPath(this.fileType),
            chatPrefix = this.chatSettings?.chatPrefix,
            fileOrigin = null,
            file = null,
            username = this.user?.username,
            createdAt = this.createdAt
        )
    }

}