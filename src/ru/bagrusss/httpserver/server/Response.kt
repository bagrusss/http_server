package ru.bagrusss.httpserver.server

import ru.bagrusss.httpserver.utils.INDEX
import ru.bagrusss.httpserver.utils.ROOT_DIR
import ru.bagrusss.httpserver.utils.STATUS_405
import ru.bagrusss.httpserver.utils.getMimeType
import java.io.File
import java.io.OutputStream
import java.util.*

/**
 * Created by vladislav
 */

val STATIC_HEADERS = "%s %s\r\nServer: bagrusss_server\r\nConnection: close\r+\nDate: %s\r\n" +
        "Content-Type: %s\r\n" + "Content-Length: %s\r\n"


class Response(protocol: String, method: String, path: String, out: OutputStream) {
    private val method = method
    private val path = path
    private var out = out
    private val protocol = protocol

    fun send() {
        if (!(method.equals("GET") || method.equals("HEAD"))) {
            print405()
            return
        }
        val path = ROOT_DIR + path
        if (path.contains("../")) {
            print404()
            return
        }
        var file = File(path)
        if (file.isDirectory) {
            file = File(path + INDEX)
            if (file.exists()) {
                print200(file)
            } else {
                print403()
            }

        }
    }

    private fun print405() {
        out.flush()
        out.write(STATIC_HEADERS.format(protocol, STATUS_405, Calendar.getInstance().time,
                getMimeType("html"), "0").toByteArray())
    }

    private fun print403() {

    }

    private fun print404() {

    }

    private fun print200(file: File?) {

    }

}