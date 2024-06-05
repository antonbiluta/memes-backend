package ru.biluta.memes.service.domain.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GiphyService(
    private val restTemplate: RestTemplate
) {

    @Value("\${giphy.active}")
    private val active: Boolean = false

    @Value("\${giphy.api-key}")
    private lateinit var apiKey: String

    @Value("\${giphy.url}")
    private lateinit var apiUrl: String

    fun searchGifs(query: String): ResponseEntity<ByteArray> {
        val url = "$apiUrl?api_key=$apiKey&q=$query&limit=1"
        val response: Map<*, *>? = restTemplate.getForObject(url, Map::class.java)
        val data = response?.get("data") as? List<*>
        val firstGif = data?.firstOrNull() as? Map<*, *>
        val images = firstGif?.get("images") as? Map<*, *>
        val original = images?.get("original") as? Map<*, *>
        val gifUrl = original?.get("url") as? String
        return if (gifUrl != null) {
            val gifResponse = restTemplate.getForEntity(gifUrl, ByteArray::class.java)
            val header = HttpHeaders()
            header.set(HttpHeaders.CONTENT_TYPE, "image/gif")

            ResponseEntity(gifResponse.body, header, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

}