package drmario

import scalafx.application.JFXApp
import scalafx.application.JFXApp.includes._
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode
import java.net.Socket
import scala.concurrent.Future
import scala.concurrent.ExecutionContext


// stuff related to drawing goes in this file

object Client extends JFXApp {
    val canvas = new Canvas(800, 800)
    val renderer = new Renderer(canvas)
    
    val sock = new Socket("localhost", 8080) // eventually will have a user input something instead of localhost
    val out = new ObjectOutputStream(sock.getOutputStream()) 
    val in = new ObjectInputStream(sock.getInputStream())  // these need to be in the opposite order from the Server


    stage = new JFXApp.PrimaryStage {
		title = "Dr. Mario"
		scene = new Scene(800, 800) {
			content = canvas

			onKeyPressed = (ke: KeyEvent) => {
                out.writeInt(98)
                out.writeObject(ke.code)
				/*ke.code match {
					case KeyCode.Left => board.leftPressed()
					case KeyCode.Right => board.rightPressed()
					case KeyCode.Up => board.upPressed()
					case KeyCode.Down => board.downPressed()
					case _ => 
				}*/
			}
			
			onKeyReleased = (ke: KeyEvent) => {
                out.writeInt(97)
                out.writeObject(ke.code)

				/*ke.code match {
					case KeyCode.Left => board.leftReleased()
					case KeyCode.Right => board.rightReleased()
					case KeyCode.Down => board.downReleased()
					case _ => 
				}*/
            }
            Future {
                while (true) {
                    val pb = in.readObject() match {
                        case board: PassableBoard => board  // if thing that gets passed isn't a passable board, this crashes
                    }
                    Plaform.runLater.renderer.render(pb) // (?)
                }
            }
		}
    }
    
}