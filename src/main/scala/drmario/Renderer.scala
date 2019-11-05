package drmario

import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color

class Renderer(canvas: Canvas) {
  val gc = canvas.graphicsContext2D

  def render(board1: PassableBoard, board2: PassableBoard): Unit = {
    gc.fill = Color.Black
    gc.fillRect(0, 0, 800, 800)
    drawBoard(board1, 0)
    drawBoard(board2, 600)
  }

  def drawBoard(board: PassableBoard, xoff: Int): Unit = {
    for (elem <- board.elements; cell <- elem.cells) {
      drawCell(cell, xoff)
    }
    for (cell <- board.currentPill.cells) {
      drawCell(cell, xoff)
    }
  }

  def drawCell(cell: PassableCell, xoff: Int): Unit = {
    val color = cell.color match {
      case DMColor.Red => Color.Red
      case DMColor.Yellow => Color.Yellow
      case DMColor.Blue => Color.Blue
    }
    gc.fill = color
    cell.style match {
      case 0 =>  // Virus
        gc.fillOval(cell.x*20 + xoff, cell.y*20, 20, 20)
      case 1 =>  // Pill
        gc.fillRect(cell.x*20 + xoff, cell.y*20, 20, 20)
    }  }
  
}