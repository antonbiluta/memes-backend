package ru.biluta.memes.service.domain

import io.minio.GetObjectArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.biluta.memes.service.config.MinioProperties
import ru.biluta.memes.service.domain.model.MemInfo
import ru.biluta.memes.service.utils.MinioExtension.getFilePath

@Service
class MinioService(
    private val minioClient: MinioClient,
    private val properties: MinioProperties
) {

    fun getPutObjectArgs(
        file: MultipartFile,
        directory: String
    ): PutObjectArgs {
        val path = "memes/$directory/${file.resource.filename}"
        return PutObjectArgs.builder()
            .bucket(properties.bucketName)
            .`object`(path)
            .stream(file.resource.inputStream, file.size, -1)
            .contentType(file.contentType ?: "application/octet-stream")
            .build()
    }

    fun uploadFile(memInfo: MemInfo): String {
        val file = memInfo.fileOrigin
        val directory = memInfo.fileType!!.path
        val args = getPutObjectArgs(file!!, directory)
        val uploadedFile = minioClient.putObject(args)
        return uploadedFile.getFilePath()
    }

    fun getFileFromStorage(
        parentFolder: String,
        objectName: String,
        objectType: String
    ): ByteArray {
        val objectPath = "$parentFolder/$objectType/$objectName"
        val args = GetObjectArgs.builder()
            .bucket(properties.bucketName)
            .`object`(objectPath)
            .build()
        val stream = minioClient.getObject(args)
        return stream.readBytes().also { stream.close() }
    }

    fun getFileFromStorage(
        path: String
    ): ByteArray {
        val args = GetObjectArgs.builder()
            .bucket(properties.bucketName)
            .`object`(path)
            .build()
        val stream = minioClient.getObject(args)
        return stream.readBytes().also { stream.close() }
    }
}