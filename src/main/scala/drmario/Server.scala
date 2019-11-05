package drmario
import java.net.ServerSocket
import java.io.ObjectOutputStream
import java.io.ObjectInputStream
import scalafx.scene.input.KeyCode
import java.net.Socket
import scala.collection.mutable
import java.util.concurrent.LinkedBlockingQueue
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Server extends App {
  case class NetworkPlayer(sock: Socket, in: ObjectInputStream, out: ObjectOutputStream, board: Board)
  case class Game(players: Array[NetworkPlayer])

  val games = mutable.Buffer[Game]()
  val playerQueue = new LinkedBlockingQueue[NetworkPlayer]()
  
  val ss = new ServerSocket(8080)
  Future {
    while (true) {
      val sock = ss.accept()
      val in = new ObjectInputStream(sock.getInputStream())
      val out = new ObjectOutputStream(sock.getOutputStream())
      val board = new Board
      playerQueue.put(NetworkPlayer(sock, in, out, board))
    }
  }

  var lastTime = System.nanoTime()
  val sendInterval = 0.05
  var sendDelay = 0.0
  while (true) {
    // Move new players from queue to buffer
    while (playerQueue.size >= 2) {
      games += Game(Array(playerQueue.poll(), playerQueue.poll()))
    }
    val time = System.nanoTime()
    val delay = (time - lastTime)/1e9
    sendDelay += delay
    val sendUpdate = sendDelay >= sendInterval
    if (sendUpdate) sendDelay = 0.0
    for (game <- games) {
      for (np <- game.players) {
        if (np.in.available() > 0) {
          val code = np.in.readInt()
          val key = np.in.readObject
          if(code == 98) {
            key match {
              case KeyEnums.Left => np.board.leftPressed()
              case KeyEnums.Right => np.board.rightPressed()
              case KeyEnums.Up => np.board.upPressed()
              case KeyEnums.Down => np.board.downPressed()
              case _ => 
            }
          } else {
            key match {
              case KeyEnums.Left => np.board.leftReleased()
              case KeyEnums.Right => np.board.rightReleased()
              case KeyEnums.Down => np.board.downReleased()
              case _ => 
            }
          }
        }
        np.board.update(delay)
      }
      if (sendUpdate) {
        val pb1 = game.players(0).board.makePassable
        val pb2 = game.players(1).board.makePassable
        for (np <- game.players) {
          np.out.writeObject(pb1)
          np.out.writeObject(pb2)
        }
      }
    }
    lastTime = time
  }
}