package mud
import akka.actor.Actor

class RoomSupervisor extends Actor {
  def receive = {
    case m => println("Unhandled message in RoomSupervisor: " + m)
  }

  val rooms = readRooms()
	def readRooms(): Map[String, Room] = {
		val xmlData = xml.XML.loadFile("map.xml")
		(xmlData \ "room").map(readRoom).map(r => r.keyword -> r).toMap
  }
  
	def readRoom(node: xml.Node): Room = {
		val keyword = (node \ "@keyword").text
		val name = (node \ "@name").text
		val description = (node \ "description").text.trim
		val exits = (node \ "exits").text.split(",")
		val item = (node \ "item").map(n => Item((n \ "@name").text,n.text.trim)).toList
		new Room(keyword, name,description,exits,item)
	}
}