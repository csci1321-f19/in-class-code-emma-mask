package mud

import akka.actor.Actor

class Room(
    val keyword: String, 
    name: String, 
    desc: String, 
    private val exits: Array[String], 
    private var items: List[Item]) extends Actor {

  import Room._
  def receive = {
    case GetItem(itemName) =>
      sender ! Player.ReceiveItem(getItem(itemName))
    case m => println("Unhandled message in Room: " + m)
  }

	def description(): String = {
		name + "\n" + desc + "\n" + printExits() + "\n" + printItems()
	}
	def printExits(): String = {
		var x = "Exits: "
		if (exits(0) != "-1") x += "north "
		if (exits(1) != "-1") x += "south "
		if (exits(2) != "-1") x += "east "
		if (exits(3) != "-1") x += "west "
		if (exits(4) != "-1") x += "up "
		if (exits(5) != "-1") x += "down "
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
		Room.rooms.get(exits(dir))
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
}