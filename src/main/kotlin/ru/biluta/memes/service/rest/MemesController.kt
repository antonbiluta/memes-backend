package ru.biluta.memes.service.rest

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.biluta.memes.service.domain.MemesService
import ru.biluta.memes.service.persistence.model.MemesApp

@RestController
@RequestMapping("/api/memes")
@Tag(name = "Memes", description = "API для работы с мемами")
class MemesController(
        val service: MemesService
) {

    @GetMapping("/{chatPrefix}/last")
    @Operation(summary = "Получить последние N мемов по префиксу чата")
    @ApiResponse(responseCode = "200")
    fun getLastMemes(
            @PathVariable chatPrefix: String,
            @RequestParam limit: Int
    ): List<MemesApp> {
       return service.findMemesByChatPrefixWithLimit(chatPrefix, 5)
    }

}