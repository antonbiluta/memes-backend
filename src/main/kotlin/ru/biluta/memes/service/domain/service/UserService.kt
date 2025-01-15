package ru.biluta.memes.service.domain.service

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.biluta.memes.service.domain.model.User
import ru.biluta.memes.service.mapping.UserMapper.toData
import ru.biluta.memes.service.mapping.UserMapper.toDomain
import ru.biluta.memes.service.persistence.repository.UserRepository
import ru.biluta.memes.service.rest.model.exception.NotFoundException

@Service
class UserService(
    private val repository: UserRepository
) {

    @Transactional(readOnly = true)
    fun getUsers(limit: Int?, offset: Int?): List<User> = when(limit != null) {
        true -> {
            val pageable = PageRequest.of(offset ?: 0, limit)
            repository.findAll(pageable).toList()
        }
        else -> repository.findAll()
    }.toDomain()

    @Transactional
    fun saveUser(user: User): User = repository.save(user.toData()).toDomain()

    @Transactional
    fun saveUsers(users: List<User>): List<User> = repository.saveAll(users.toData()).toDomain()

}