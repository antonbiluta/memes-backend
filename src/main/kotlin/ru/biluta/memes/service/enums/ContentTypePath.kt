package ru.biluta.memes.service.enums

enum class ContentTypePath(
    val contentType: String,
    val path: String,
    val fileExtension: String
) {

    IMAGE("image/jpeg", "image", ".jpg"),
    GIF("video/mp4", "video", ".mp4"),
    VIDEO("video/mp4", "video", ".mp4");


    companion object {
        fun getContentTypeByPath(externalPath: String): ContentTypePath? {
            return entries.firstOrNull { it.path == externalPath }
        }
    }
}