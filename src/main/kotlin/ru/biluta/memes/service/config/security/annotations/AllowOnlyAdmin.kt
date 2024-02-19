package ru.biluta.memes.service.config.security.annotations

import io.swagger.v3.oas.annotations.security.SecurityRequirement

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
@SecurityRequirement(name = "adminBasic")
annotation class AllowOnlyAdmin
