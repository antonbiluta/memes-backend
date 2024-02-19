package ru.biluta.memes.service.mapping

import ru.biluta.memes.service.domain.model.User
import ru.biluta.memes.service.rest.model.requests.UserRequest
import ru.biluta.memes.service.rest.model.responses.UserResponse
import ru.biluta.memes.service.persistence.model.User as UserData

object UserMapper {

    fun UserRequest.toDomain(): User {
        return User(
                id = null,
                userId = this.userId,
                username = this.username,
                firstName = this.firstName,
                lastName = this.lastName
        )
    }

    fun List<User>.toData(): List<UserData> {
        return this.map { it.toData() }
    }

    fun User.toData(): UserData {
        return UserData(
                userId = this.userId,
                username = this.username,
                firstName = this.firstName,
                lastName = this.lastName
        )
    }

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

    fun List<User>.toResponses(): List<UserResponse> {
        return this.map { it.toResponse() }
    }

    fun User.toResponse(): UserResponse {
        return UserResponse(
            id = this.id,
            userId = this.userId,
            username = this.username
        )
    }

}