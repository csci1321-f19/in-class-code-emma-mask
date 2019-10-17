package adt

class SinglyLinkedList[A] extends collection.mutable.Buffer[A] {
    private class Node(var data: A, var next: Node)
    
    private var head: Node = null

    def length: Int = ???

    def +=(a: A) = {
        
    } // append

    def +=:(elem: A) = ??? // prepend

    def insert(a: A, index: Int): Unit = ???

    def apply(index: Int): A = ???

    def update(index: Int, a: A): Unit = ???

    def clear(): Unit = ???

    def insertAll(n: Int,elems: Traversable[A]): Unit = ???

    def remove(n: Int): A = ???
  
    def iterator: Iterator[A] = ???
}

