package drmario

trait BoardElement {
    def cells: List[Cell]
    def removeAll(locs: List[(Int, Int)]): Option[BoardElement]
    def supported(grid: Map[(Int, Int), BoardElement]): Boolean
    def move(dx: Int, dy: Int, grid: Map[(Int, Int), BoardElement]): BoardElement
    def makePassable: PassableElement
    def cellAt(x: Int, y: Int): Option[Cell] = cells.find(c => c.x == x && c.y == y)
}