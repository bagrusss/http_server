package ru.bagrusss.httpserver

import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options

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
        println(Runtime.getRuntime().availableProcessors())
        return
    }
    try {
        val port = Integer.valueOf(cmd.getOptionValue('p'))
        val cpu = Integer.valueOf(cmd.getOptionValue('c'))
        val root = cmd.getOptionValue('r')
        val queue = Integer.valueOf(cmd.getOptionValue('q'))
        val server = Server(port, root, cpu, queue);
        server.start()
    } catch(e: Exception) {
        help(options)
    }
}

fun help(options: Options) {
    HelpFormatter().printHelp("Help", options)
}



