package basics

import graphicgame.Entity

/**
This is a basic main for you to start off with.
*/
object HelloWorld {
	def main(args: Array[String]): Unit = {
		println("Hello Emma!")

		val e1 = new Entity(0, 0)
		val e2 = new Entity(2, 0)
		println(e1.intersects(e2))
	}
	
	def square(x: Double) = x*x
	
	def cube(x: Double) = x*x*x
}
