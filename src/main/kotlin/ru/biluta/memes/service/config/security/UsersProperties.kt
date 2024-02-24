package ru.biluta.memes.service.config.security

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "users")
class UsersProperties(
    val admin: Auth
) {

    class Auth(
        val username: String,
        val password: String?
    )
}