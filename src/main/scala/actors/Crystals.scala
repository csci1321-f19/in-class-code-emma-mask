package actors

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.image.WritableImage
import scalafx.scene.image.ImageView
import akka.actor.ActorSystem
import akka.actor.Props
import scalafx.stage.WindowEvent

object Crystals extends JFXApp {
  val system = ActorSystem("Crystals")
  val img = new WritableImage(800, 800)
  val manager = system.actorOf(Props(new CrystalManager(img)))


  stage = new JFXApp.PrimaryStage {
    title = "Crystals!"
    scene = new Scene(800, 800) {
      val view = new ImageView(img)
      content = view
    }
    onCloseRequest = (we: WindowEvent) => system.terminate()
  }
}