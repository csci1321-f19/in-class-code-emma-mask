package actors
import akka.actor.Actor
import scalafx.scene.image.WritableImage
import akka.actor.Props
import scalafx.scene.paint.Color
import scalafx.application.Platform

class CrystalManager(img: WritableImage) extends Actor {
  val reader = img.pixelReader.get  // This is only safe because all WritableImages have defined readers.
  val writer = img.pixelWriter
  for (i <- img.width().toInt/4 until 3*img.width().toInt/4) writer.setColor(i, img.height().toInt-1, Color.Black)
  for (_ <- 1 to 12) {
    val child = context.actorOf(Props[Floaty])
    child ! Floaty.MoveFrom(util.Random.nextInt(img.width().toInt), 0)
  }

  import CrystalManager._   // Import that messages that this actor accepts.
  def receive = {
    case CheckMove(nx, ny, ox, oy) =>
      if (ny >= img.height()) sender ! Floaty.MoveFrom(util.Random.nextInt(img.width().toInt), 0)  // Wrap back to the top if they fall of the bottom.
      else if (nx < 0 || nx >= img.width()) sender ! Floaty.MoveFrom(ox, oy) // Went out of bounds in x so don't accept new value.
      else if (reader.getColor(nx, ny) == Color.Black) {  // New position is crystal so "stick" at old location.
        Platform.runLater(writer.setColor(ox, oy, Color.Black))
        sender ! Floaty.MoveFrom(util.Random.nextInt(img.width().toInt), 0)
      } else sender ! Floaty.MoveFrom(nx, ny)   // Accept new location and move again.
    case m => println("Unhandled message in manager: " + m)
  }
}

object CrystalManager {
  case class CheckMove(newx: Int, newy: Int, oldx: Int, oldy: Int)
}