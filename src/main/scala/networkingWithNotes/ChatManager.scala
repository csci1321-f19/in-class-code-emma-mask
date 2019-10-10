package networkingWithNotes
/*
import akka.actor.Actor
import java.net.Socket
import java.io.PrintStream
import java.io.BufferedReader

class ChatManager extends Actor {
    def recieve = {
        case NewChatter(name, sock, out, in) =>
            context.actorOf(Props(new Chatter(name, sock, out, in)), name)
        case CheckAllInput =>
            for(child <- context.children) child ! Chatter.CheckInput
        case SendToAll => 
            for(child <- context.children) child ! Chatter.PrintMessage()

        case m => println("unhandled message in ChatManager: "+m)
    }
}

object ChatManager {
    case class NewChatter(name: String, sock: Socket, out: PrintStream, in: BufferedReader)
    case object CheckAllInput
    case class SendToAll(msg: String)
}
*/