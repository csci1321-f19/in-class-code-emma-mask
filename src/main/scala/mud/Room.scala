package mud

import akka.actor.Actor

class Room(
    val keyword: String, 
    name: String, 
    desc: String, 
    private val exits: Array[String], 
    private var items: List[Item]) extends Actor {

	private var exits: Array[Option[ActorRef]] = null

  import Room._
  def receive = {
	case LinkExits(rooms) =>
	exits = exitKeys.Map(key => rooms.get(key)) // get returns option
		// if it returns -1, you get none
		// if not, you get whatever room is connected
    case GetItem(itemName) =>
	  sender ! Player.ReceiveItem(getItem(itemName))
	case DropItem =>
	case GetExit =>
	case GetDescription => 
    case m => println("Unhandled message in Room: " + m)
  }

	def description(): String = {
		name + "\n" + desc + "\n" + printExits() + "\n" + printItems()
	}
	def printExits(): String = {
		var x = "Exits: "
		if (exits(0).nonEmpty) x += "north "
		if (exits(1).nonEmpty) x += "south "
		if (exits(2).nonEmpty) x += "east "
		if (exits(3).nonEmpty) x += "west "
		if (exits(4).nonEmpty) x += "up "
		if (exits(5).nonEmpty) x += "down "
		x
	}
	def printItems(): String = {
		var y = "Items: "
		for (item <- items) {
			y += item.name + " "
		}
		y
	}
	def getExit(dir: Int): Option[ActorRef] = {
		exits(dir)
	}
	def getItem(itemName: String): Option[Item] = {
		var y: Boolean = false
		var z:Int = 0
		for (x <- items){
			if(!y) z = z + 1
			if (x.name == itemName) y = true
		}
		if (y) {
			val gotItem = Some(items(z - 1))
			items = items.filter(x => x != gotItem.get)
			gotItem
		}
		else {
			None}
		}
	def dropItem(item: Item): Unit = {
		items = item :: items
	}
}

object Room {
  case class GetItem(itemName: String)
  case class DropItem(item: Item)
  case class GetExit(dir: Int)
  case object GetDescription
  case class LinkExits(rooms: Map[String, ActorRef])   // how rooms figure out what they're connected to
}