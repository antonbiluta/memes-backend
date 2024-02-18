package ru.biluta.memes.service.rest.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.biluta.memes.service.rest.model.exception.BadRequestException
import ru.biluta.memes.service.rest.model.exception.RequestException

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler
    fun handleIllegalStateException(
        ex: BadRequestException
    ): ResponseEntity<RequestException> {
        return RequestException(
            code = ex.code,
            description = ex.description,
            details = ex.details
        ).let { ResponseEntity(it, ex.status) }
    }

}