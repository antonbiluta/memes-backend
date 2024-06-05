package ru.biluta.memes.service.domain.service

import io.minio.GetObjectArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import okio.ByteString.Companion.readByteString
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.biluta.memes.service.config.minio.MinioProperties
import ru.biluta.memes.service.domain.model.MemInfo
import ru.biluta.memes.service.enums.ContentTypePath
import ru.biluta.memes.service.utils.MinioExtension.getFilePath
import java.security.MessageDigest

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

    fun uploadTestFile(
        file: ByteArray,
        chatId: Long,
        userId: Long,
        contentDir: ContentTypePath
    ): String {
        val inputStream = file.inputStream()

        val fileBytes = inputStream.readByteString(30).hex()
        inputStream.reset()
        val fileName = "$userId-$fileBytes-30${contentDir.fileExtension}"
        val path = "memes/$chatId/${contentDir.path}/$fileName"

        val args = PutObjectArgs.builder()
            .bucket(properties.bucketName)
            .`object`(path)
            .stream(inputStream, file.size.toLong(), -1)
            .contentType(contentDir.contentType)
            .userMetadata(mapOf(
                "ownerId" to "$chatId",
                "chatId" to "$chatId"
            ))
            .build()
        val uploadedFile = minioClient.putObject(args)
        return uploadedFile.getFilePath()
    }

    fun getPutObjectArgs(
        memInfo: MemInfo
    ): PutObjectArgs {
        val file = memInfo.fileOrigin!!
        val chatDir = memInfo.chatId.toString()
        val contentDir = memInfo.fileType!!
        val fileName = getUniqueFileName(file, chatDir)
        val path = "memes/$chatDir/${contentDir.path}/$fileName"
        return PutObjectArgs.builder()
            .bucket(properties.bucketName)
            .`object`(path)
            .stream(file.resource.inputStream, file.size, -1)
            .contentType(file.contentType ?: "application/octet-stream")
            .userMetadata(mapOf(
                "ownerId" to "${memInfo.userId}",
                "chatId" to "${memInfo.chatId}"
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
    ): String {
        val hash = generateHash(file.bytes)
        return "$chatDir-$hash"
    }

    private fun generateHash(
        content: ByteArray
    ): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(content)
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}