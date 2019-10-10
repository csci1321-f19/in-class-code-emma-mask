package drmario

class Board {
  import Board._
  private var _elements = List.fill[BoardElement](10)(new Virus(util.Random.nextInt(width), 3+util.Random.nextInt(13), DMColor.randomColor()))
  private var _currentPill = new Pill(List(
    new PillPiece(3, 0, DMColor.randomColor()),
    new PillPiece(4, 0, DMColor.randomColor())
  ))
  private var _nextPill = new Pill(List(
    new PillPiece(3, 0, DMColor.randomColor()),
    new PillPiece(4, 0, DMColor.randomColor())
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

  private var checkSupport = false

  def leftPressed() = leftHeld = true
  def rightPressed() = rightHeld = true
  def upPressed() = upHeld = true
  def downPressed() = downHeld = true
  def leftReleased() = leftHeld = false
  def rightReleased() = rightHeld = false
  def downReleased() = downHeld = false

  def makePassable: PassableBoard = {
    PassableBoard(elements.map(_.makePassable), currentPill.makePassable)
  }

  def update(delay: Double): Unit = {
    dropDelay += delay
    moveDelay += delay
    if (dropDelay >= dropInterval && !checkSupport) {
      val grid = elements.flatMap(be => be.cells.map(c => (c.x, c.y) -> be)).toMap
      tryDrop(grid)
      dropDelay = 0.0
    }
    if (moveDelay >= moveInterval) {
      val grid = elements.flatMap(be => be.cells.map(c => (c.x, c.y) -> be)).toMap
      if (checkSupport) {
        if (!dropUnsupported(grid)) {
          if (removePieces(elements.flatMap(be => be.cells.map(c => (c.x, c.y) -> be)).toMap) == 0) checkSupport = false
        }
      } else {
        if(leftHeld) _currentPill = currentPill.move(-1, 0, grid)
        if(rightHeld) _currentPill = currentPill.move(1, 0, grid)
        if(upHeld) {
          upHeld = false
          _currentPill = currentPill.rotate(grid)
        }
        if(downHeld) tryDrop(grid)
      }
      moveDelay = 0.0
    }
  }

  def tryDrop(grid: Map[(Int, Int), BoardElement]): Unit = {
    if (_currentPill.allowMove(0, 1, grid)) {
      _currentPill = currentPill.move(0, 1, grid)
    } else {
      _elements ::= currentPill
      val newGrid = grid ++ currentPill.cells.map(c => (c.x, c.y) -> currentPill)
      if (removePieces(newGrid) > 0) {
        checkSupport = true
      }
      _currentPill = _nextPill
      _nextPill = new Pill(List(
        new PillPiece(3, 0, DMColor.randomColor),
        new PillPiece(4, 0, DMColor.randomColor)
      ))
    }
  }

  def removePieces(grid: Map[(Int, Int), BoardElement]): Int = {
    var toRemove = Set[(Int, Int, BoardElement)]()

    def checkNext(i: Int, j: Int, streak: List[(Int, Int, BoardElement)], lasti: Int, lastj: Int): List[(Int, Int, BoardElement)] = {
      grid.get(i -> j) match {
        case None => 
          if (streak.length >= 4) {
            println(streak)
            toRemove ++= streak
          }
          Nil
        case Some(be) =>
          if (streak.nonEmpty && streak.head._3.cellAt(lasti, lastj).get.color == be.cellAt(i, j).get.color) {
            (i, j, be) :: streak
          } else {
            if (streak.length >= 4) {
              println(streak)
              toRemove ++= streak
            }
            List((i, j, be))
          }
      }
    }
    // Check columns
    for (i <- 0 until width) {
      var streak = grid.get(i -> 0).map(be => (i, 0, be)).toList
      for (j <- 1 until height) {
        streak = checkNext(i, j, streak, i, j-1)
      }
      if (streak.length >= 4) toRemove ++= streak
    }
    // Check rows
    for (j <- 0 until height) {
      var streak = grid.get(0 -> j).map(be => (0, j, be)).toList
      for (i <- 1 until width) {
        streak = checkNext(i, j, streak, i-1, j)
      }
      if (streak.length >= 4) toRemove ++= streak
    }
    val removeMap = toRemove.toList.groupBy(_._3)
    _elements = elements.flatMap(e => if (removeMap.contains(e)) e.removeAll(removeMap(e).map(t => t._1 -> t._2)) else Some(e))
    toRemove.size
  }

  def dropUnsupported(grid: Map[(Int, Int), BoardElement]): Boolean = {
    val dropping = elements.filter(!_.supported(grid)).toSet
    _elements = elements.map(e => if (dropping.contains(e)) e.move(0, 1, grid) else e)
    dropping.nonEmpty
  }
}

object Board {
  val width = 8
  val height = 16
}