package drmario

class Board {
  private var _elements = List.fill[BoardElement](10)(new Virus(util.Random.nextInt(8), 3+util.Random.nextInt(13), DMColor.randomColor()))
  private var _currentPill = new Pill(List(
    new PillPiece(3, 0, DMColor.Red),
    new PillPiece(4, 0, DMColor.Blue)
  ))
  private var _nextPill = new Pill(List(
    new PillPiece(3, 0, DMColor.Red),
    new PillPiece(4, 0, DMColor.Blue)
  ))

  def elements = _elements
  def currentPill = _currentPill

  val dropInterval = 1.0
  private var dropDelay = 0.0
  val moveInterval = 0.1
  private var moveDelay = 0.0
  private var leftHeld = false
  private var rightHeld = false
  private var upHeld = false
  private var downHeld = false

  def leftPressed() = leftHeld = true
  def rightPressed() = rightHeld = true
  def upPressed() = upHeld = true
  def downPressed() = downHeld = true
  def leftReleased() = leftHeld = false
  def rightReleased() = rightHeld = false
  def upReleased() = upHeld = false
  def downReleased() = downHeld = false

  def update(delay: Double): Unit = {
    dropDelay += delay
    moveDelay += delay
    if (dropDelay >= dropInterval) {
      tryDrop()
      dropDelay = 0.0
    }
    if (moveDelay >= moveInterval) {
      if(leftHeld) _currentPill = currentPill.move(-1, 0)
      if(rightHeld) _currentPill = currentPill.move(1, 0)
      if(upHeld) ???
      if(downHeld) tryDrop()
      moveDelay = 0.0
    }
  }

  def tryDrop(): Unit = {
    if (_currentPill.allowMove(0, 1)) {
      _currentPill = currentPill.move(0, 1)
    } else {
      _currentPill = _nextPill
      // TODO add current to elements
      // TODO change next
    }
  }
}