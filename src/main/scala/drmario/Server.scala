package drmario
import java.net.ServerSocket
import java.io.ObjectOutputStream
import java.io.ObjectInputStream


// should have no scalaFX imports
    // will have to replace AnimationTimer with a while loop

// things involving commands (not drawing stuff) go in this file

object Server extends App {
    val board = new PassableBoard

    val ss = new ServerSocket(8080)
    val sock = ss.accept()  // blocking call, but we'll fix that later
    val in = new ObjectInputStream(sock.getInputStream())  // wrap because you don't want to send bytes
    val out = new ObjectOutputStream(sock.getOutputStream()) 
    // don't buffer network stuff because then you'd have to flush the buffer

    var lastTime = -1L
    while (true) {
        val time = System.nanoTime()
        if(lastTime > 0) {
                val delay = (time - lastTime)/1e9
                board.update(delay)
                val pb = board.makePassable
                out.writeInt(99)
                out.writeObject(pb) // why our passable board has to be serialized
        }
        lastTime = time
    }
}