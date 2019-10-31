package drmario

case class Virus(x: Int, y: Int, color: DMColor.Value) extends Cell with BoardElement {
  def cells: List[Cell] = List(this)
  def removeAll(locs: List[(Int, Int)]): Option[BoardElement] = None
  def supported(grid: Map[(Int, Int), BoardElement]): Boolean = true
  def move(dx: Int, dy: Int, grid: Map[(Int, Int), BoardElement]): BoardElement =
    throw new UnsupportedOperationException("Tried to move a virus.")
  def makePassable: PassableElement(List[PassableCell(x, y, color, 0)])
}
