package mud

class Player {

}

object Player {
  case class ReceiveItem(oitem: Option[Item])
  case class TakeItem(oitem: Option[Item])
  case class TakeExit()
}