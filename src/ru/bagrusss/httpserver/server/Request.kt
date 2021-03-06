package ru.bagrusss.httpserver.server

import java.io.BufferedReader
import java.net.URLDecoder
import java.util.*

/**
 * Created by bagrusss
 */

class Request {

    private val br: BufferedReader
    private val method = StringBuilder()
    private val path = StringBuilder()
    private val headers = HashMap<String, String>()
    private val protocol = StringBuilder()

    constructor(bufferedReader: BufferedReader) {
        br = bufferedReader
        var devider: Int
        while (true) {
            var buffer = br.readLine();
            if (buffer == null || buffer.trim().isEmpty()) {
                // println(headers.toString())
                break
            }
            var pos = buffer.indexOf(' ');
            var buf2: String
            devider = isHeader(buffer)
            if (devider == -1) {
                protocol.append(buffer.substring(buffer.lastIndexOf(' ') + 1))
                method.append(buffer.substring(0, pos).toUpperCase())
                buf2 = buffer.substring(pos + 1)
                path.append(URLDecoder.decode(buf2.substring(0, buf2.indexOf(' ')), "UTF-8"))
            } else {
                headers.put(buffer.substring(0, devider), buffer.substring(devider + 2))
            }
        }
    }

    private fun isHeader(s: String): Int {
        return s.indexOf(": ")
    }

    fun getPath() = path.toString()

    fun getMethod() = method.toString()

    fun getProtocol() = protocol.toString()

    fun getHeaders() = headers

}