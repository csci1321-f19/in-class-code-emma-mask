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
    
    def iterator = new Iterator[(K, V)] {
        // making an in-order iterator
        // have to have something (the stack) that stores memory to keep track of parents you've already visited
        val stack = mutable.Stack[Node[K, V]]()

        def pushAllLeft(n: Node[K, V]): Unit = {
            if(n != null) {
                stack.push(n)
                pushAllLeft(n.left)
            }
        }
        pushAllLeft(root)

        def hasNext: Boolean = stack.nonEmpty
        def next(): (K, V) = {
            val ret = stack.pop()
            pushAllLeft(ret.right) // doesn't do anything if a node doesn't have anything to its right
            (ret.key, ret.value)
        }
    }

    override def update(key: K, value: V): Unit = ???

    def -=(key: K) = {
        // find it
        // check special cases (0 or 1 children)
        // otherwise, replace with the smallest node on the right
        ???
    }

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

    def preorder(visitor: (K, V) => Unit): Unit = {
        def helper(n: Node[K, V]): Unit = {
            if (n != null) {
                visitor(n.key, n.value)
                helper(n.left)
                helper(n.right)
            }
        }
        helper(root)
    }

    def postorder(visitor: (K, V) => Unit): Unit = {
        def helper(n: Node[K, V]): Unit = {
            if (n != null) {
                helper(n.left)
                helper(n.right)
                visitor(n.key, n.value)
            }
        }
        helper(root)
    }

    def inorder(visitor: (K, V) => Unit): Unit = {
        def helper(n: Node[K, V]): Unit = {
            if (n != null) {
                helper(n.left)
                visitor(n.key, n.value)
                helper(n.right)
            }
        }
        helper(root)
    }

}

object BSTMap {
    private class Node[K, V](val key: K, var value: V, var left: Node[K, V], var right: Node[K, V])
        // don't use case class because it's mutable
}