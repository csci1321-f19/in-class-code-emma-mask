package drmario

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.animation.AnimationTimer
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode

object Main extends JFXApp {
	val canvas = new Canvas(800, 800)
	val renderer = new Renderer(canvas)
	val board = new Board
	
	stage = new JFXApp.PrimaryStage {
		title = "Dr. Mario"
		scene = new Scene(800, 800) {
			content = canvas

			onKeyPressed = (ke: KeyEvent) => {
				ke.code match {
					case KeyCode.Left => board.leftPressed()
					case KeyCode.Right => board.rightPressed()
					case KeyCode.Up => board.upPressed()
					case KeyCode.Down => board.downPressed()
					case _ => 
				}
			}
			
			onKeyReleased = (ke: KeyEvent) => {
				ke.code match {
					case KeyCode.Left => board.leftReleased()
					case KeyCode.Right => board.rightReleased()
					case KeyCode.Down => board.downReleased()
					case _ => 
				}
			}
			
			var lastTime = -1L
			val timer = AnimationTimer(time => {
				if(lastTime > 0) {
					val delay = (time - lastTime)/1e9
					board.update(delay)
					val pb = board.makePassable
					renderer.render(pb, pb)
				}
				lastTime = time
			})
			timer.start()
		}
	}
}