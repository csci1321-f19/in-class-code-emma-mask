package adt

class DoublyLinkedList[A] extends collection.mutable.Buffer[A] {    
    private var hd: Node[A] = null
    private var tl: Node[A] = null
    private val end: Node[A] = new Node[A](null, null.asInstanceOf[A], null)    // this is the sentinel
    end.prev = end      // makes sentinel hug itself
    end.next = end      // makes sentinel hug itself
    private var numElems = 0

    def length: Int = ???

    def +=(a: A) = {
        val n = new Node[A](end.prev, a, end)
        end.prev.next = n 
        numElems += 1
        this
    } // append

    def +=:(elem: A) = {
        val n = new Node[A](end, elem, end.next)
        end.prev.next = n 
        end.next = n
        numElems += 1
        this
    } // prepend

    def insert(a: A, index: Int): Unit = ???

    def apply(index: Int): A = {
        require(index >= 0 && index < numElems)
        var rover = end.next
        for (i <- 0 until index) rover = rover.next
        rover.data
    }

    def update(index: Int, a: A): Unit = {
        require(index >= 0 && index < numElems)
        var rover = end.next
        for (i <- 0 until index) rover = rover.next
        rover.data = a
    }

    def clear(): Unit = {
        end.next = end
        end.prev = end
        numElems = 0
    }

    def insertAll(n: Int,elems: Traversable[A]): Unit = ???     // have to make rover point to n+1 if you're inserting at node n (?)

    def remove(index: Int): A = {
        require(index >= 0 && index < numElems)
        numElems -= 1
        var rover = end.next
        for (i <- 0 until index) rover = rover.next     // index - 1 so that you stop at the index before the thing you want to remove
        val ret = rover.data
        rover.next.prev = rover.prev
        rover.prev.next = rover.next     // makes rover point to the thing after the thing you want to remove
        ret
    }   // gotcha is removing from head or tail (?)
  
    def iterator = new Iterator[A] {
        // abstracts out walking through the collection
        // don't walk through list using an integer index - instead, use .next
        // use this inside a loop

        private var rover = end.next
        def hasNext: Boolean = rover != end // rover will never be null
        def next(): A = {
            val ret = rover.data
            rover = rover.next
            ret
        }
    }     
}

object DoublyLinkedList {
    private class Node(var prev: Node[A], var data: A, var next: Node[A])
}