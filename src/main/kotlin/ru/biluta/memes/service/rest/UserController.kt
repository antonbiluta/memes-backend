package ru.biluta.memes.service.rest

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import ru.biluta.memes.service.config.security.annotations.AllowOnlyAdmin
import ru.biluta.memes.service.domain.service.UserService
import ru.biluta.memes.service.mapping.UserMapper.toResponses
import ru.biluta.memes.service.mapping.UserMapper.toDomain
import ru.biluta.memes.service.mapping.UserMapper.toResponse
import ru.biluta.memes.service.rest.model.requests.UserRequest
import ru.biluta.memes.service.rest.model.responses.UserResponse

@RestController
@RequestMapping("/admin/users")
@Tag(name = "Users", description = "API для работы с пользователями")
class UserController(
    private val service: UserService
) {

    @AllowOnlyAdmin
    @GetMapping
    @Operation(summary = "Получить всех пользователей")
    @ApiResponse(responseCode = "200")
    fun getUsers(
        @RequestParam limit: Int?,
        @RequestParam offset: Int?
    ): List<UserResponse> {
        return service.getUsers(limit, offset).toResponses()
    }

    @AllowOnlyAdmin
    @PostMapping(consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Сохранить информацию о пользователе")
    @ApiResponse(responseCode = "200")
    fun saveUser(
        @RequestBody request: UserRequest
    ): UserResponse {
        val user = request.toDomain()
        return service.saveUser(user).toResponse()
    }

    @AllowOnlyAdmin
    @PostMapping("/all")
    @Operation(summary = "Сохранить список пользователей")
    @ApiResponse(responseCode = "200")
    fun saveUsers(
        @RequestBody request: List<UserRequest>
    ): List<UserResponse> {
        val users = request.map { it.toDomain() }
        return service.saveUsers(users).toResponses()
    }

}