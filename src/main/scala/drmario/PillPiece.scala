package drmario

case class PillPiece(x: Int, y: Int, color: DMColor.Value) extends Cell {
  def move(dx: Int, dy: Int): PillPiece = {
    new PillPiece(x + dx, y + dy, color)
  }

  def allowMove(dx: Int, dy: Int, grid: Map[(Int, Int), BoardElement]): Boolean = {
    val nx = x+dx
    val ny = y+dy
    nx >= 0 && nx < 8 && ny < 16 && !grid.contains((nx, ny))
  }
}