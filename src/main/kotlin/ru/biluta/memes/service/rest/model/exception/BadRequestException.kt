package ru.biluta.memes.service.rest.model.exception

import org.springframework.http.HttpStatus

open class BadRequestException(
    val status: HttpStatus = HttpStatus.BAD_REQUEST,
    val code: String?,
    val description: String?,
    val details: ErrorDetails?
) : RuntimeException() {

    constructor(status: HttpStatus, code: String?, description: String?) : this(status, code, description, null)

    constructor(status: HttpStatus, code: String?) : this(status, code, null, null)

    constructor(code: String?, description: String?) : this(HttpStatus.BAD_REQUEST, code, description, null)

    constructor(code: String?): this(HttpStatus.BAD_REQUEST, code, null, null)

}