package ru.bagrusss.httpserver

import java.net.ServerSocket
import java.util.concurrent.ForkJoinPool

/**
 * Created by vladislav
 */

class Server(port: Int, dir: String,
             cpu: Int = Runtime.getRuntime().availableProcessors(), queue: Int = 120) {

    private var mPort = port
    private var mDir = dir
    private var mCPU = cpu
    private var mIsRunning = true
    private val serverSocket: ServerSocket
    private val mPool: ForkJoinPool
    val mConfig =
            """
Server runned!
port: $mPort
root dir: $mDir
cpu count: $mCPU
"""

    init {
        serverSocket = ServerSocket(mPort, queue)
        mPool = ForkJoinPool(mCPU)
    }

    fun start() {
        printConfig()
        while (mIsRunning) {
            var socket = serverSocket.accept()
            mPool.execute({

            })
        }
    }

    fun stop() {
        mIsRunning = false
    }

    private fun printConfig() {
        println(mConfig)
    }

}