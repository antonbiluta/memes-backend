package ru.biluta.memes.service.domain.service

import io.minio.GetObjectArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import okio.ByteString.Companion.readByteString
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.biluta.memes.service.config.minio.MinioProperties
import ru.biluta.memes.service.domain.model.MemInfo
import ru.biluta.memes.service.utils.MinioExtension.getFilePath

@Service
class MinioService(
    private val minioClient: MinioClient,
    private val properties: MinioProperties
) {

    fun uploadFile(memInfo: MemInfo): String {
        val args = getPutObjectArgs(memInfo)
        val uploadedFile = minioClient.putObject(args)
        return uploadedFile.getFilePath()
    }

    fun getPutObjectArgs(
        memInfo: MemInfo
    ): PutObjectArgs {
        val file = memInfo.fileOrigin!!
        val chatDir = memInfo.chatId.toString()
        val contentDir = memInfo.fileType!!
        val fileName = getUniqueFileName(file, chatDir, contentDir.fileExtension)
        val path = "memes/$chatDir/${contentDir.path}/$fileName"
        return PutObjectArgs.builder()
            .bucket(properties.bucketName)
            .`object`(path)
            .stream(file.resource.inputStream, file.size, -1)
            .contentType(file.contentType ?: "application/octet-stream")
            .userMetadata(mapOf(
                "owner id" to "${memInfo.chatId}",
                "chat id" to "${memInfo.chatId}"
            ))
            .build()
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

    private fun getUniqueFileName(
        file: MultipartFile,
        chatDir: String,
        fileExtension: String
    ): String {
        val fileBytes = file.inputStream.readByteString(30).hex()
        return "$chatDir-$fileBytes-30$fileExtension"
    }
}