package drmario

class Board {
  private var _elements = List[BoardElement](new Virus(2, 2, DMColor.Red))

  def elements = _elements
}