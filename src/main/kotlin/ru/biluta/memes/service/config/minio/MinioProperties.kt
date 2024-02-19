package ru.biluta.memes.service.config.minio

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "minio")
class MinioProperties (
    val endpoint: String,
    val secretKey: String,
    val accessKey: String,
    val bucketName: String
)