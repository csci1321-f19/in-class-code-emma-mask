package mudWithNotes
import akka.actor.Actor

class RoomSupervisor extends Actor {
  def receive = {
	case AddPlayerToRoom(player, keyword) =>
		player ! Player.TakeExit(rooms.get(keyword))
    case m => println("Unhandled message in RoomSupervisor: " + m)
  }

  val rooms = readRooms()
	for (child <- context.children) child ! Room.LinkExits(rooms) // tells all of the rooms to go find their exits
	  
	def readRooms(): Map[String, ActorRef] = {
		val xmlData = xml.XML.loadFile("map.xml")
		(xmlData \ "room").map(readRoom).toMap
  }
  
	def readRoom(node: xml.Node): (String, ActorRef) = {
		val keyword = (node \ "@keyword").text
		val name = (node \ "@name").text
		val description = (node \ "description").text.trim
		val exits = (node \ "exits").text.split(",")
		val item = (node \ "item").map(n => Item((n \ "@name").text,n.text.trim)).toList
		keyword -> context.actorOf(Props(new Room(keyword, name,description,exits,item)), keyword) //makes the children
	}
}

object RoomSupervisor {
	case class AddPlayerToRoom(player: ActorRef, keyword: String) // this might be the only message that RoomSupervisor recieves
}