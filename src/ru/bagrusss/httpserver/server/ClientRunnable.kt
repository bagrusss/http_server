package ru.bagrusss.httpserver.server

import java.io.*
import java.net.Socket

/**
 * Created by vladislav
 */

class ClientRunnable : Runnable {
    var socket: Socket
    val size = 5
    var isEmpty: Boolean = false
        get() {
            println("log ")
            return this.isEmpty
        }

    private var input: InputStream
    private var output: OutputStream
    private var bufferedReader: BufferedReader

    constructor(s: Socket) {
        socket = s
        input = s.inputStream
        output = s.outputStream
        bufferedReader = BufferedReader(InputStreamReader(input))
    }

    override fun run() {
        try {
            var request = Request(bufferedReader)
            val method = request.getMethod()
            val path = request.getPath()
            val protocol = request.getProtocol()
            var response = Response(protocol, method, path, output)
            response.send()
        } catch (e: IOException) {
            println(e.cause)
        } finally {
            try {
                closeResourses()
            } catch(e: IOException) {
                println(e.message)
            }
        }
    }

    private fun closeResourses() {
        socket.close()
        input.close()
        output.close()
        bufferedReader.close()
    }
}