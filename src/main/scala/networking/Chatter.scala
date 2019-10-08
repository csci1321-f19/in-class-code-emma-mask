package networking

import akka.actor.Actor
import java.net.Socket
import java.io.PrintStream
import java.io.BufferedReader


class Chatter(name: String, sock: Socket, out: PrintStream, in: BufferedReader) extends Actor {
    def recieve = {
        case CheckInput => // can't have a blocking call here
            if(in.ready()) {    // want to only get stuff from the inbox if it has something (?)
                val message = in.readLine()
                context.parent ! ChatManager.SendToAll()

            }
        case PrintMessage(msg) =>
            out.println(msg)

        case m => println("unhandled message in ChatManager: "+m)
    }
}

object Chatter {
    case object CheckInput
    case class PrintMessage
}