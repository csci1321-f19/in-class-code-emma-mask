package drmario

class PillPiece(val x: Int, val y: Int, val color: DMColor.Value) extends Cell {
  def move(dx: Int, dy: Int): PillPiece = {
    new PillPiece(x + dx, y + dy, color)
  }

  def allowMove(dx: Int, dy: Int): Boolean = {
    val nx = x+dx
    val ny = y+dy
    nx >= 0 && nx < 8 && ny < 16
  }

}