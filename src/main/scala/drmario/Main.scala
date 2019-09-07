package drmario

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas

object Main extends JFXApp {
	val canvas = new Canvas(800, 800)
	val renderer = new Renderer(canvas)
	val board = new Board
	
	stage = new JFXApp.PrimaryStage {
		title = "Dr. Mario"
		scene = new Scene(800, 800) {
			content = canvas
			renderer.render(board)
		}
	}
}