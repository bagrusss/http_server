package ru.bagrusss.httpserver.utils

import java.util.*

/**
 * Created by vladislav
 */

class MimeTypes {
    val mMimeTypes = HashMap<String, String>()

    constructor() {
        mMimeTypes.put("html", "text/html")
        mMimeTypes.put("css", "text/css")
        mMimeTypes.put("js", "text/javascript")
        mMimeTypes.put("jpg", "image/jpeg")
        mMimeTypes.put("jpeg", "image/jpeg")
        mMimeTypes.put("png", "image/png")
        mMimeTypes.put("gif", "image/gif")
        mMimeTypes.put("swf", "application/x-shockwave-flash")
        mMimeTypes.put("json", "application/json")
    }

    fun getMimeType(s: String): String? {
        return mMimeTypes[s]
    }
}

val mimeType = MimeTypes()

fun getMimeType(s: String) = mimeType.getMimeType(s)

val HTTP_VERSION = "HTTP/1.1"
val STATUS_200 = "200 OK"
val STATUS_403 = "403 Forbidden"
val STATUS_404 = "404 Not Found"
val STATUS_405 = "405 Method Not Allowed"

