package adt

object Graphs extends App {
    val g = Array(
        Array(0, 1, 1, 0, 0, 0),
        Array(0, 0, 1, 0, 0, 0),
        Array(0, 0, 0, 1, 1, 0),
        Array(1, 0, 0, 0, 0, 0),
        Array(0, 1, 0, 1, 1, 0),
        Array(1, 0, 1, 0, 0, 0)
    )

    def reachable(node1: Int, node2: Int, connect: Array[Array[Int]]): Boolean = {
        // MUD will have shortest path, not reachable
        def helper(n1: Int, visited: Set[Int]): Boolean = {
            // nodes in MUD would be passed around as Strings for the keywords
            if (node1 == node2) true else {
                val ret = false
                val newVisited = visited + n1
                for (n <- 0 until connect.length) {
                    if (connect(node1)(n) != 0 && !visited(n)) {
                        ret ||= helper(n, newVisited)
                        // as soon as ret = true, reachable() doesn't get called again
                    }
                }
                ret
            }
        }
        helper(node1, Set.empty)
    }

    def shortestPath(node1: Int, node2: Int, connect: Array[Array[Int]]): Int = {
        // MUD will have shortest path, not reachable
        def helper(n1: Int, visited: Set[Int]): Int = {
            // nodes in MUD would be passed around as Strings for the keywords
            if (node1 == node2) 0 else {
                val ret = 1000000000
                val newVisited = visited + n1
                for (n <- 0 until connect.length) {
                    if (connect(node1)(n) != 0 && !visited(n)) {
                        ret = ret min helper(n, newVisited) // makes ret equal to the smaller of the two
                    }
                }
                ret + 1
            }
        }
        helper(node1, Set.empty)
    }

    println(reachable(0, 4, g))
    println(reachable(0, 5, g))
    println(shortestPath(0, 4, g))
    println(shortestPath(0, 5, g))
    println(shortestPath(5, 0, g))
}
