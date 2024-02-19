package ru.biluta.memes.service.rest

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.biluta.memes.service.domain.service.MemesService
import ru.biluta.memes.service.mapping.MemMapper.toApi
import ru.biluta.memes.service.mapping.MemMapper.toDomain
import ru.biluta.memes.service.rest.model.exception.NotFoundException
import ru.biluta.memes.service.rest.model.requests.MemInfoRequest
import ru.biluta.memes.service.rest.model.responses.MemInfoResponse

@RestController
@RequestMapping("/api/memes")
@Tag(name = "Memes", description = "API для работы с мемами")
class MemesController(
    private val service: MemesService
) {

    @GetMapping("/{chatPrefix}/last")
    @Operation(summary = "Получить последние N мемов по префиксу чата")
    @ApiResponse(responseCode = "200")
    fun getLastMemes(
        @PathVariable chatPrefix: String,
        @RequestParam limit: Int
    ): List<MemInfoResponse> {
        val info = service.findMemesByChatPrefixWithLimit(chatPrefix, limit)
            ?: throw NotFoundException("not.found", "Не найдено")
        return info.map { it.toApi() }
    }

    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Загрузить мем")
    @ApiResponse(responseCode = "200")
    fun uploadMeme(
        @RequestBody memInfo: MemInfoRequest,
    ): ResponseEntity<String> {
        val fileInfo = service.saveMeme(memInfo.toDomain())
        return ResponseEntity.ok("Путь в хранилище: $fileInfo")
    }

}