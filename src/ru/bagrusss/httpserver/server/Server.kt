package ru.bagrusss.httpserver.server

import ru.bagrusss.httpserver.utils.ROOT_DIR
import java.net.ServerSocket
import java.util.concurrent.ForkJoinPool

/**
 * Created by vladislav
 */

class Server(port: Int, dir: String,
             cpu: Int = Runtime.getRuntime().availableProcessors(), queue: Int = 120) {

    private val mPort = port
    private val mDir = dir
    private val mCPU = cpu
    private val mQueue = queue
    private var mIsRunning = true
    private val serverSocket: ServerSocket
    private val mPool: ForkJoinPool
    private val mConfig =
            """
Server runned!
port: $mPort
root dir: $mDir
cpu count: $mCPU
queue count: $mQueue
"""

    init {
        mPool = ForkJoinPool(mCPU * 2 + 1)
        serverSocket = ServerSocket(mPort, queue)
        ROOT_DIR = mDir
    }

    fun start() {
        printConfig()
        while (mIsRunning) {
            var socket = serverSocket.accept()
            var task = ClientRunnable(socket)
            mPool.execute(task)
        }
    }

    fun stop() {
        mIsRunning = false
    }

    private fun printConfig() {
        println(mConfig)
    }

}