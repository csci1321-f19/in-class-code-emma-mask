package adt
import scala.reflect.ClassTag

class ArrayQueue[A : ClassTag] extends MyQueue[A] {
  private var front = 0
  private var back = 0
  private var data: Array[A] = Array.fill(10)(null.asInstanceOf[A])

  def enqueue(value: A): Unit = {
    if ((back + 1) % data.length == front) {
      val tmp = Array.fill(data.length * 2)(null.asInstanceOf[A])
      for (i <- 0 until data.length - 1) {
        tmp(i) = data((i + front) % data.length)
      }
      front = 0
      back = data.length - 1
      data = tmp
    }
    data(back) = value
    back = (back + 1) % data.length
  }
  def dequeue(): A = {
    val ret = data(front)
    front = (front + 1) % data.length
    ret
  }
  def peek(): A = data(front)
  def isEmpty: Boolean = front == back
}