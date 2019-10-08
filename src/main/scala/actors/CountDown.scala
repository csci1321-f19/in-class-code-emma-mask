package actors
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.ActorRef

class CountDownActor extends Actor {
  def receive = {
    case StartCountingDown(n, other, yetAnother) =>
      println(n)
      other ! CountDown(n - 1, yetAnother)
    case CountDown(n, other) =>
      if (n >= 0) {
        println(n)
        other ! CountDown(n - 1, sender)
      } else {
        Thread.sleep(100)
        context.system.terminate()
      }
    case m => println("Unhandled message in CoutDownActor " + m)
  }
}

case class StartCountingDown(n: Int, other: ActorRef, yetAnother: ActorRef)
case class CountDown(n: Int, other: ActorRef)

object CountDown {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("CountDown")

    val a1 = system.actorOf(Props[CountDownActor])
    val a2 = system.actorOf(Props[CountDownActor])
    val a3 = system.actorOf(Props[CountDownActor])

    a1 ! StartCountingDown(10000, a2, a3)
  }
}