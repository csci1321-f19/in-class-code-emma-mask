package drmario

class Virus(val x: Int, val y: Int, val color: DMColor.Value) extends Cell with BoardElement {
  def cells: List[Cell] = List(this)
}