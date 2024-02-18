package ru.biluta.memes.service.enums

enum class ContentTypePath(
    val contentType: String,
    val path: String
) {

    IMAGE("image", "image"),
    GIF("image/gif", "gif"),
    VIDEO("video", "video");


    companion object {
        fun getContentTypeByPath(externalPath: String): ContentTypePath? {
            return entries.firstOrNull { it.path == externalPath }
        }
    }
}