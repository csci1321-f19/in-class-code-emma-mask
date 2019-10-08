package actors
import akka.actor.Actor

class Floaty extends Actor {
  import Floaty._
  def receive = {
    case MoveFrom(x, y) =>
      var dx = 0
      var dy = 0
      while (dx == 0 && dy == 0) {
        dx = util.Random.nextInt(3) - 1
        dy = util.Random.nextInt(2)
      }
      sender ! CrystalManager.CheckMove(x + dx, y + dy, x, y)
    case m => println("Unhandled message in floaty: " + m)
  }
}

object Floaty {
  case class MoveFrom(x: Int, y: Int)
}