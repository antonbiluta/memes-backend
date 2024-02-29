package ru.biluta.memes.service.rest

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.biluta.memes.service.config.security.annotations.AllowOnlyAdmin
import java.util.*

@RestController
@RequestMapping("/api/admin/users")
@Tag(name = "Users", description = "API для работы с пользователями")
class TestController {

    @AllowOnlyAdmin
    @GetMapping
    @Operation(summary = "Получить всех пользователей")
    @ApiResponse(responseCode = "200")
    fun getUsers(
            @CookieValue("ife_test_cookie") marketDevice: String?,
            response: HttpServletResponse
    ): ResponseEntity<String> {
        val cookie = if (marketDevice != null) {
            Cookie("ife_test_cookie", marketDevice)
        } else {
            Cookie("ife_test_cookie", UUID.randomUUID().toString())
        }
        cookie.apply {
            domain = "biluta.ru"
            path = "/"
            isHttpOnly = true
            maxAge = 60*60
        }
        response.addCookie(cookie)
        return ResponseEntity.ok("OKEY")
    }

}