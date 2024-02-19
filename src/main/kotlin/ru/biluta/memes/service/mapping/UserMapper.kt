package ru.biluta.memes.service.mapping

import ru.biluta.memes.service.domain.model.User
import ru.biluta.memes.service.rest.model.responses.UserResponse
import ru.biluta.memes.service.persistence.model.User as UserData

object UserMapper {

    fun List<UserData>.toDomain(): List<User> {
        return this.map { it.toDomain() }
    }

    fun UserData.toDomain(): User {
        return User(
            id = this.id,
            userId = this.userId,
            username = this.username,
            firstName = this.firstName,
            lastName = this.lastName
        )
    }

    fun List<User>.toApi(): List<UserResponse> {
        return this.map { it.toApi() }
    }

    fun User.toApi(): UserResponse {
        return UserResponse(
            id = this.id,
            userId = this.userId,
            username = this.username
        )
    }

}