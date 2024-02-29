package ru.biluta.memes.service.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.biluta.memes.service.enums.ContentTypePath
import ru.biluta.memes.service.persistence.model.Memes
import ru.biluta.memes.service.persistence.model.MemesApp
import ru.biluta.memes.service.persistence.model.User
import ru.biluta.memes.service.persistence.repository.MemesAppRepository
import ru.biluta.memes.service.persistence.repository.MemesRepository
import ru.biluta.memes.service.persistence.repository.UserRepository

@Service
class TestsService(
    private val memesAppRepository: MemesAppRepository,
    private val userRepository: UserRepository,
    private val memesRepository: MemesRepository,
    private val minioService: MinioService
) {

    @Transactional
    fun indexAllUserFromOldTable(memesApps: List<MemesApp>? = null): Boolean {
        val rows = memesApps ?: memesAppRepository.findAll()
        val checkRows = userRepository.findAll().map { it.userId }
        rows.distinctBy { it.userId }
            .filterNot { checkRows.contains(it.userId) }
            .map {
                User(
                    userId = it.userId,
                    username = it.author,
                    firstName = null,
                    lastName = null
                )
            }.let { users ->
                userRepository.saveAll(users)
            }
        return true
    }

    @Transactional
    fun indexAllMemesFromOldTable(memesApps: List<MemesApp>? = null): Boolean {
        val rows = memesApps ?: memesAppRepository.findAll()
        val memes = rows.distinctBy { it.media }.map {
            val contentInfo = it.mediaType?.let { mediaType ->
                ContentTypePath.getContentTypeByPath(mediaType.lowercase())
            } ?: ContentTypePath.IMAGE
            val filePath = minioService.uploadTestFile(
                it.media,
                it.chatId,
                it.userId,
                contentInfo
            )
            Memes(
                chatId = it.chatId,
                userId = it.userId,
                filePath = filePath,
                fileType = contentInfo.path,
                createdAt = it.createdAt
            )
        }
        memesRepository.saveAll(memes)
        return true
    }

    @Transactional
    fun indexOldTable() {
        memesAppRepository.findAll().let {
            val indexUsers = indexAllUserFromOldTable(it)
            if (indexUsers) {
                indexAllMemesFromOldTable(it)
            }
        }
    }

}