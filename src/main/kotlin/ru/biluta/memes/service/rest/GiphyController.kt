package ru.biluta.memes.service.rest

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.biluta.memes.service.domain.service.GiphyService

@RestController
@RequestMapping("/api/giphy")
class GiphyController(
    private val giphyService: GiphyService
) {

    @Value("\${giphy.active}")
    private var active: Boolean = false

    @GetMapping("/search")
    fun searchGifs(@RequestParam query: String): ResponseEntity<ByteArray> {
        if (!active) {
            return ResponseEntity.badRequest().body(ByteArray(0))
        }
        return giphyService.searchGifs(query)

    }

}