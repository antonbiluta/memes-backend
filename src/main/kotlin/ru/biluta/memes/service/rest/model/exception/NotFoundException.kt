package ru.biluta.memes.service.rest.model.exception

import org.springframework.http.HttpStatus

class NotFoundException(
    code: String?,
    description: String?,
    details: ErrorDetails?
) : BadRequestException(HttpStatus.NOT_FOUND, code, description, details) {

    constructor(code: String?) : this(code, null, null)

    constructor(code: String?, description: String?) : this(code, description, null)

}