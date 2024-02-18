package ru.biluta.memes.service.domain

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.biluta.memes.service.domain.model.User
import ru.biluta.memes.service.mapping.UserMapper.toDomain
import ru.biluta.memes.service.persistence.repository.UserRepository
import ru.biluta.memes.service.rest.model.exception.NotFoundException

@Service
class UserService(
    private val repository: UserRepository
) {

    @Transactional(readOnly = true)
    fun getUsers(limit: Int?, offset: Int?): List<User> {
        val pageable = limit?.let { PageRequest.of(offset ?: 0, limit) }
        val result = when (pageable != null) {
            true -> repository.findAll(pageable).toList()
            else -> repository.findAll()
        }.toDomain()
        if (result.isEmpty()) {
            throw NotFoundException("not.found", "Не найдено")
        }
        return result
    }

}