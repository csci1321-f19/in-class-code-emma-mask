package adt

class UnsortedLinkedList[A](higherP: (A, A) => Boolean) extends MyPriorityQueue[A] {
    private val end: Node[A] = new Node[A](null, null.asInstanceOf[A], null)    // sentinel
    end.prev = end
    end.next = end
    private var numElems = 0

    def enqueue(value: A): Unit = {
        val n = new Node[A](end.prev, a, end)
        end.prev.next = n
        end.prev = n
        numElems += 1
        this
    }

    def dequeue(): A = {
        
    }

    def peek(): A
    def isEmpty: Boolean

    def findHighestPriority(): Node = {
        val rover = end.next
        val tempNode = rover
        while (rover != end) {
            if(tempNode.data >= rover.next.data) {
                rover = rover.next
            } else {
                val tempNode = rover.next
                rover = rover.next
            }
        }
    }
}

object UnsortedLinkedList {
    private class Node(var prev: Node[A], var data: A, var next: Node[A])
}