package drmario

case class Pill(pieces: List[PillPiece]) extends BoardElement {
  def cells: List[Cell] = pieces
  def removeAll(locs: List[(Int, Int)]): Option[BoardElement] = {
    val newPieces = pieces.filter(p => !locs.contains(p.x -> p.y))
    if (newPieces.isEmpty) None else Some(Pill(newPieces))
  }
  def supported(grid: Map[(Int, Int), BoardElement]): Boolean = {
    pieces.exists(p => p.y == Board.height-1 || grid.get(p.x -> (p.y + 1)).map(_ != this).getOrElse(false))
  }

  def move(dx: Int, dy: Int, grid: Map[(Int, Int), BoardElement]): Pill = {
    if(allowMove(dx, dy, grid)) new Pill(pieces.map(_.move(dx, dy))) else this
  }

  def makePassable: PassableElement = {
    PassableElement(cells.map(c => PassableCell(c.x, c.y, c.color, 1)))
  }

  def rotate(grid: Map[(Int, Int), BoardElement]): Pill = {
    pieces match {
      case Nil => this
      case h :: Nil => this
      case p1 :: p2 :: Nil if p1.y == p2.y =>
        new Pill(List(p2.move(0, -1), p1.move(1, 0)))
      case p1 :: p2 :: Nil =>
        new Pill(List(p1.move(-1, 1), p2))
      case _ => 
        println("Pill with more than two pieces!!!")
        this
    }
  }

  def allowMove(dx: Int, dy: Int, grid: Map[(Int, Int), BoardElement]): Boolean = {
    pieces.forall(_.allowMove(dx, dy, grid))
  }
}