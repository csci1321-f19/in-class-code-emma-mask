package videocode

import scala.collection.parallel

object Parallel extends App {
    for(i <- (1 to 1000).par) {
        println(i*i)
    }
}
