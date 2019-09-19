package drmario

class Pill(val pieces: List[PillPiece]) extends BoardElement {
    def cells: List[Cell] = pieces
  
    def move(dx: Int, dy: Int): Pill = {
      if(allowMove(dx, dy)) new Pill(pieces.map(_.move(dx, dy))) else this
    }
  
    def allowMove(dx: Int, dy: Int): Boolean = {
      pieces.forall(_.allowMove(dx, dy))
    }
  }