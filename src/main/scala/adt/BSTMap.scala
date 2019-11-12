package adt

import collection.mutable

class BSTMap[K, V](lt: (K, K) => Boolean) extends mutable.Map[K, V] {
    import BSTMap._

    private var root: Node[K, V] = null
        // start off as null because you don't have any keys in the tree

    def get(key: K): Option[V] = {
        var rover = root
        while (rover !- null && rover.key != key) {
            if (lt(key, rover/key)) rover = rover.left else rover = rover = rover.right
        }
        if (rover != null) rover.value = value
    }
    
    def iterator: Iterator[(K, V)] = ???
    override def update(key: K, value: V): Unit = ???

    def -=(key: K) = ???

    def +=(kv: (K, V)) = {
        def helper(n: Node[K, V]): Node[K, V] = {
            if (n == null) {
                root = new Node[K, V](kv._1, kv._2, null, null)
            } else if (n.key == kv._1) {
                // if keys are the same, update value
                n.value = kv._2
                n
            } else if (lt(n.key, kv._1)) {
                n.left = helper(m.left)
                    // what lets you reassign null nodes to data
                n
            } else {
                n.right = helper(n.right)
                n
            }
            n
        }
        root = helper(root)
        this
    }

}

object BSTMap {
    private class Node[K, V](val key: K, var value: V, var left: Node[K, V], var right: Node[K, V])
        // don't use case class because it's mutable
}