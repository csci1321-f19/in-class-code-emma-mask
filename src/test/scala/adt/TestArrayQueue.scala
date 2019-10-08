package adt

import org.junit.Test
import org.junit.Assert._

class TestArrayQueue {
  @Test def startEmpty(): Unit = {
    val q = new ArrayQueue[Int]()
    assertTrue(q.isEmpty)
  }

  @Test def add1(): Unit = {
    val q = new ArrayQueue[Int]()
    assertTrue(q.isEmpty)
    q.enqueue(7)
    assertEquals(7, q.peek())
    assertFalse(q.isEmpty)
    assertEquals(7, q.dequeue())
    assertTrue(q.isEmpty)
  }

  @Test def moreThan10(): Unit = {
    val q = new ArrayQueue[Int]()
    val nums = Array.fill(100)(util.Random.nextInt)
    for (n <- nums) q.enqueue(n)
    assertFalse(q.isEmpty)
    for (n <- nums) {
      assertEquals(n, q.peek())
      assertEquals(n, q.dequeue())
    }
    assertTrue(q.isEmpty)
  }
}