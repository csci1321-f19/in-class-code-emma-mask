package networkingWithNotes

import akka.actor.ActorSystem
import akka.actor.Props
import java.net.ServerSocket
import java.io.PrintStream
import java.io.BufferedReader
import java.io.InputStreamReader
import scala.concurrent.Future
import scala.concurrent.duration._

object ActorChat extends App {
    val system = ActorSystem("Chat")

    val manager = system.actorOf(Props[ChatManager], "manager")
    
    implicit val ec = system.dispacher // how actor manages the execution context

    system.scheduler.schedule(1.second, 0.1 seconds, manager, ChatManager.CheckAllInput) // have to make system.dispatcher first


    // step 1 - set up networking starting with the server
    // step 2 - make it so that multiple people can connect
    val ss = new ServerSocket(4040)     // have to use "new" because Java
    while (true) {
        val sock = ss.accept() // accept is a blocking call, but it's fine here
        Future {    // use Future so that in.readLine() doesn't block for other users
            val out = new PrintStream(sock.getOutputStream()) // use output stream to ask person their name
            out.println("What is your name?")
            val in = new BufferedReader(new InputStreamReader(sock.getInputStream()))   // what we'll change Console.in to
            val name = in.readLine()
            println(name+" has connected")
            manager ! ChatManager.NewChatter(name, sock, out, in) 
        }
    }



}