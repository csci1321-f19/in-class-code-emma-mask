package adt

trait ListQueue[A] extends MyQueue[A]{
    import ListQueue._

    private var front: Node[A] = null
    private var back: Node[A] = null

    def enqueue(value: A): Unit = {
        back.next = new Node[A](value, null) // gets the last node to connect to the new node
        back = back.next // makes back point to the new node

    }

    def dequeue(): A = {
        val ret = front.data
        front = front.next
        if(front.next == null) back = null
        ret
    }
    
    def peek(): A = front.data
    
    def isEmpty: Boolean = front == null // could also use end == null, but would have to write stuff differently
}

object ListQueue {
    private class Node[A](data: A, next: Node[A])
}