package ru.bagrusss.httpserver.server

import org.apache.commons.io.IOUtils
import ru.bagrusss.httpserver.utils.*
import java.io.File
import java.io.FileInputStream
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

    private val HTTP_DEVIDER = "\r\n"

    fun send() {
        if (!(method.equals("GET") || method.equals("HEAD"))) {
            print405()
            return
        }
        val path = ROOT_DIR + path.split("?")[0]
        if (path.contains("../")) {
            print404()
            return
        }
        var file = File(path)
        if (file.isDirectory) {
            file = File(path + INDEX)
            if (file.exists()) {
                print200(file, method.equals("GET"))
                return
            } else {
                print403()
                return
            }
        }
        if (file.exists()) {
            print200(file, method.equals("GET"))
        } else {
            print404()
        }
    }

    private fun print405() {
        out.flush()
        out.write(STATIC_HEADERS.format(
                protocol,
                STATUS_405,
                Calendar.getInstance().time,
                getMimeType("html"),
                "0").toByteArray())
    }

    private fun print403() {
        out.flush()
        out.write(STATIC_HEADERS.format(
                protocol,
                STATUS_403,
                Calendar.getInstance().time,
                getMimeType("html"),
                "0").toByteArray())
    }

    private fun print404() {
        out.flush()
        out.write(STATIC_HEADERS.format(
                protocol,
                STATUS_404,
                Calendar.getInstance().time,
                getMimeType("html"),
                "0").toByteArray())
    }

    private fun print200(file: File, isGet: Boolean) {
        var exten = ""
        val i = file.absolutePath.lastIndexOf('.');
        if (i > 0) {
            exten = file.absolutePath.substring(i + 1);
        }
        val mime = getMimeType(exten)
        if (mime == null) {
            print403()
            return
        }
        out.flush()
        out.write(STATIC_HEADERS.format(
                protocol,
                STATUS_200,
                Calendar.getInstance().time,
                mime,
                file.length()
        ).toByteArray())
        out.write(HTTP_DEVIDER.toByteArray())
        if (isGet) {
            FileInputStream(file).use {
                out.write(IOUtils.toByteArray(it))
            }
        }
    }
}