package adt

trait MyQueue[A] {
  def enqueue(value: A): Unit
  def dequeue(): A
  def peek(): A
  def isEmpty: Boolean
}