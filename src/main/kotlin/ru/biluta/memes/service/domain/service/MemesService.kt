package ru.biluta.memes.service.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.biluta.memes.service.domain.model.MemInfo
import ru.biluta.memes.service.mapping.MemMapper.toData
import ru.biluta.memes.service.mapping.MemMapper.toDomain
import ru.biluta.memes.service.persistence.repository.MemesRepository
import ru.biluta.memes.service.rest.model.exception.NotFoundException
import ru.biluta.memes.service.utils.KotlinExtension.basePageable
import ru.biluta.memes.service.utils.constants.Exceptions.CODE_MEMES_NOT_FOUND
import ru.biluta.memes.service.utils.constants.Exceptions.MEMES_NOT_FOUND

@Service
class MemesService(
    private val memesRepository: MemesRepository,
    private val minioService: MinioService
) {

    @Transactional(readOnly = true)
    fun findMemesByChatPrefixWithLimit(
        chatPrefix: String,
        userId: Long?,
        limit: Int
    ): List<MemInfo> {
        val result = when (userId != null) {
            true -> memesRepository.findMemesByChatPrefixAndUserId(chatPrefix, userId, limit.basePageable())
            else -> memesRepository.findMemesByChatPrefix(chatPrefix, limit.basePageable())
        } ?: throw NotFoundException(CODE_MEMES_NOT_FOUND, MEMES_NOT_FOUND)
        return result.map { it.toDomain() }
    }

    @Transactional
    fun saveMeme(
        memInfo: MemInfo
    ): MemInfo {
        return memInfo.apply {
            filePath = getFilePathFromStorage()
        }.saveMem()
    }

    private fun MemInfo.getFilePathFromStorage(): String {
        return minioService.uploadFile(this)
    }

    private fun MemInfo.saveMem(): MemInfo {
        return memesRepository.save(
            this.toData()
        ).toDomain()
    }

}