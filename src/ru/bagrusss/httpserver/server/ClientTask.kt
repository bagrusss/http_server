package ru.bagrusss.httpserver.server

import java.io.*
import java.net.Socket
import java.util.concurrent.RecursiveAction

/**
 * Created by vladislav on 11.03.16.
 */
class ClientTask : RecursiveAction {
    private var socket: Socket
    private var input: InputStream
    private var output: OutputStream
    private var bufferedReader: BufferedReader
    private var bufferedWriter: BufferedWriter

    constructor(s: Socket) {
        socket = s
        input = s.inputStream
        output = s.outputStream
        bufferedReader = BufferedReader(InputStreamReader(input))
        bufferedWriter = BufferedWriter(OutputStreamWriter(output))
    }

    override fun compute() {
        try {
            var request = Request(bufferedReader)
            val method = request.getMethod()
            val path = request.getPath()
            var response = Response(method, path)
            response.send()
        } catch (e: IOException) {

        } finally {
            try {
                closeResourses()
            } catch(e: IOException) {

            }
        }
    }

    private fun closeResourses() {
        socket.close()
        input.close()
        output.close()
        bufferedReader.close()
        bufferedWriter.close()
    }
}