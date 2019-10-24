package adt

class UnsortedLinkedList[A](higherP: (A, A) => Boolean) extends MyPriorityQueue[A] {
    def enqueue(value: A): Unit = {
        var rove
    }
    def dequeue(): A
    def peek(): A
    def isEmpty: Boolean
}

object UnsortedLinkedList {
    private class Node(var end: Node[A], var data: A, var front: Node[A])
}