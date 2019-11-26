package adt
import scala.reflect.ClassTag

class UnsortedArrayPQ[A : ClassTag](higherP: (A, A) => Boolean) extends MyPriorityQueue[A] {
  private var arr = Array.fill(10)(null.asInstanceOf[A])
  private var front = 0

  def enqueue(value: A): Unit = ???
  def dequeue(): A = ???
  def peek(): A = ???
  def isEmpty: Boolean = front == 0
}