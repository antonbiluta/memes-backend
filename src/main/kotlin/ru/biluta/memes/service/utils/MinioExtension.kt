package ru.biluta.memes.service.utils

import io.minio.ObjectWriteResponse

object MinioExtension {

    fun ObjectWriteResponse.collectFilepath(): String =
        "${this.bucket()}/${this.`object`()}"

}