package ru.biluta.memes.service.rest

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import ru.biluta.memes.service.config.security.annotations.AllowOnlyAdmin
import ru.biluta.memes.service.domain.UserService
import ru.biluta.memes.service.mapping.UserMapper.toApi
import ru.biluta.memes.service.mapping.UserMapper.toDomain
import ru.biluta.memes.service.rest.model.requests.UserRequest
import ru.biluta.memes.service.rest.model.responses.UserResponse

@RestController
@RequestMapping("/api/admin/users")
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
        return service.getUsers(limit, offset).toApi()
    }

    @AllowOnlyAdmin
    @PostMapping(consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    @Operation(summary = "Сохранить информацию о пользователе")
    @ApiResponse(responseCode = "200")
    fun saveUser(
        @ModelAttribute request: UserRequest
    ): UserResponse {
        return service.saveUser(request.toDomain()).toApi()
    }

}