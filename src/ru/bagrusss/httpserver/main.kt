package ru.bagrusss.httpserver

import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options
import org.apache.commons.cli.ParseException
import java.net.BindException

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
    var port = 0
    try {
        port = Integer.valueOf(cmd.getOptionValue('p'))
        val cpu = Integer.valueOf(cmd.getOptionValue('c'))
        val root = cmd.getOptionValue('r')
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



