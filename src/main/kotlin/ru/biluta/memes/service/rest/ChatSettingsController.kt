package ru.biluta.memes.service.rest

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.biluta.memes.service.domain.ChatSettingsService
import ru.biluta.memes.service.mapping.ChatMapper.toApi
import ru.biluta.memes.service.rest.model.requests.ChatEditRequest
import ru.biluta.memes.service.rest.model.responses.ChatResponse

@RestController
@RequestMapping("/api/chat")
@Tag(name = "ChatSettings", description = "API для работы с настройками чата")
class ChatSettingsController(
    private val service: ChatSettingsService
) {

    @GetMapping("/{chat}")
    @Operation(summary = "Получить настройки чата")
    @ApiResponse(responseCode = "200")
    fun getChatSettings(
        @PathVariable chat: String
    ): ChatResponse {
        return service.getChatSettingsByChat(chat).toApi()
    }

    @PutMapping("/{chatId}/update", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(summary = "Обновить настройки чата")
    @ApiResponse(responseCode = "200")
    fun updateChatSettings(
        @PathVariable chatId: Long,
        @ModelAttribute request: ChatEditRequest
    ): ChatResponse {
        return service.updatePrefixById(chatId, request).toApi()
    }

}