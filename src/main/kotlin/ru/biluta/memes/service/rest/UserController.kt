package ru.biluta.memes.service.rest

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import ru.biluta.memes.service.domain.UserService
import ru.biluta.memes.service.mapping.UserMapper.toApi
import ru.biluta.memes.service.rest.model.responses.UserResponse

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "API для работы с пользователями")
class UserController(
    private val service: UserService
) {

    @GetMapping
    @Operation(summary = "Получить всех пользователей")
    @ApiResponse(responseCode = "200")
    fun getLastMemes(
        @RequestParam limit: Int?,
        @RequestParam offset: Int?
    ): List<UserResponse> {
        return service.getUsers(limit, offset).toApi()
    }

}