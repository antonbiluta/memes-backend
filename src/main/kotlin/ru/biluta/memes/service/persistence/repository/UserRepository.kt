package ru.biluta.memes.service.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.biluta.memes.service.persistence.model.User

interface UserRepository : JpaRepository<User, Long>