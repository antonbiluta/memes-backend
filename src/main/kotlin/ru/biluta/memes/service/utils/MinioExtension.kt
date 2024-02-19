package ru.biluta.memes.service.utils

import io.minio.ObjectWriteResponse

object MinioExtension {

    fun ObjectWriteResponse.getFilePath(): String {
        val bucket = this.bucket()
        val pathInBucket = this.`object`()
        return "$bucket/$pathInBucket"
    }

}