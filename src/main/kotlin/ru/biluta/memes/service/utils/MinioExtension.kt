package ru.biluta.memes.service.utils

import io.minio.ObjectWriteResponse

object MinioExtension {

    fun ObjectWriteResponse.getFilePath(): String = this.`object`()

}