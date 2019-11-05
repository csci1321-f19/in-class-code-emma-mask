package adt

import org.junit.Test
import org.junit.Assert._
import org.junit.Before

class TestSeq {
  private var lst: SinglyLinkedList[Int] = null 

  @Before def makeList(): Unit = {
    lst = new SinglyLinkedList[Int]()
  }

  @Test def emptyOnCreate(): Unit = {
    assertEquals(0, lst.length)
  }

  @Test def addOne(): Unit = {
    lst += 7
    assertEquals(1, lst.length)
    assertEquals(7, lst(0))
  }

  @Test def addRemoveTwo(): Unit = {
    lst += 7 += 3
    assertEquals(2, lst.length)
    assertEquals(7, lst(0))
    assertEquals(3, lst(1))
    lst -= 7
    assertEquals(3, lst(0))
  }
}