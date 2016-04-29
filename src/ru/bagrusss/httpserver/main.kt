package ru.bagrusss.httpserver

import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options
import org.apache.commons.cli.ParseException
import ru.bagrusss.httpserver.server.Server
import java.io.File
import java.net.BindException
import java.nio.file.Paths

/**
 * Created by vladislav
 */

fun main(args: Array<String>) {

    val options = Options()
    options.addOption("h", "help", false, "Shows help")
    options.addOption("c", "cpu", true, "int. Count of CPU for use")
    options.addOption("r", "root", true, "String. Root directory")
    options.addOption("p", "port", true, "int. Server port")
    options.addOption("q", "queue", true, "int. Socket queue")
    var cmd = DefaultParser().parse(options, args)
    if (cmd.hasOption('h')) {
        help(options)
        System.exit(0)
    }
    var port = 0
    try {
        port = Integer.valueOf(cmd.getOptionValue('p'))
        val cpu = Integer.valueOf(cmd.getOptionValue('c'))
        val cpu_count = Runtime.getRuntime().availableProcessors()
        if (cpu > cpu_count) {
            println("Max count of cpu is $cpu_count")
            System.exit(1)
        }
        val root = cmd.getOptionValue('r').replace("~", System.getProperty("user.home"))
        val path = Paths.get(root).toString()
        val file = File(path)
        if (!file.canRead() || !file.exists()) {
            println("I can't read this directory: $path")
            System.exit(2)
        }
        val queue = Integer.valueOf(cmd.getOptionValue('q'))
        val server = Server(port, root, cpu, queue);
        server.start()
    } catch(e: ParseException) {
        help(options)
    } catch(e: BindException) {
        println(e.message + "port $port")
    }
}

fun help(options: Options) {
    HelpFormatter().printHelp("Help", options)
}



