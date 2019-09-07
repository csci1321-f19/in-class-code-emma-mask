package drmario

import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color

class Renderer(canvas: Canvas) {
  def render(board: Board): Unit = {
    val gc = canvas.graphicsContext2D
    gc.fill = Color.Black
    gc.fillRect(0, 0, 800, 800)
    for (elem <- board.elements; cell <- elem.cells) {
      val color = cell.color match {
        case DMColor.Red => Color.Red
        case DMColor.Yellow => Color.Yellow
        case DMColor.Blue => Color.Blue
      }
      gc.fill = color
      cell match {
        case v: Virus =>
          gc.fillOval(v.x*20, v.y*20, 20, 20)
        case pp: PillPiece =>
      }
    }
  }
  
}