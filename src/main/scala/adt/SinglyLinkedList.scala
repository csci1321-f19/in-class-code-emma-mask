package adt

class SinglyLinkedList[A] extends collection.mutable.Buffer[A] {    
    private var hd: Node[A] = null
    private var tl: Node[A] = null

    def length: Int = ???

    def +=(a: A) = {
        
    } // append

    def +=:(elem: A) = ??? // prepend

    def insert(a: A, index: Int): Unit = ???

    def apply(index: Int): A = {
        require(index >= 0 && index < numElems)
        var rover = hd
        for (i <- 0 until index) rover = rover.next
        rover.data
    }

    def update(index: Int, a: A): Unit = {
        require(index >= 0 && index < numElems)
        var rover = hd
        for (i <- 0 until index) rover = rover.next
        rover.data = a
    }

    def clear(): Unit = ???

    def insertAll(n: Int,elems: Traversable[A]): Unit = ???     // have to make rover point to n+1 if you're inserting at node n (?)

    def remove(index: Int): A = {
        require(index >= 0 && index < numElems)
        if (index == 0) {
            val ret = hd.data
            hd = hd.next
            if (hd.next == null) tl = null
            ret
        } else {
            var rover = hd
            for (i <- 0 until index - 1) rover = rover.next     // index - 1 so that you stop at the index before the thing you want to remove
            val ret = rover.next.data
            rover.next = rover.next.next        // makes rover point to the thing after the thing you want to remove
            if (rover.next == null) tl = rover 
            ret
        }
    }   // gotcha is removing from head or tail (?)
  
    def iterator = new Iterator[A] {
        // abstracts out walking through the collection
        // don't walk through list using an integer index - instead, use .next
        // use this inside a loop

        private var rover = hd
        def hasNext: Boolean = rover != null
        def next(): A = {
            val ret = rover.data
            rover = rover.next
            ret
        }
    }     
}

object SinglyLinkedList {
    private class Node(var data: A, var next: Node)
}