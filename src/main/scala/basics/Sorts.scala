package basics

object Sorts extends App {
  def bubbleSortOrdered[A <% Ordered[A]](arr: Array[A]): Unit = {
    for (i <- 0 until arr.length-1) {
      for (j <- 0 until arr.length-1-i) {
        if (arr(j) > arr(j+1)) {
          val tmp = arr(j)
          arr(j) = arr(j+1)
          arr(j+1) = tmp
        }
      }
    }
  }

  def bubbleSort[A](arr: Array[A])(gt: (A, A) => Boolean): Unit = {
    for (i <- 0 until arr.length-1) {
      for (j <- 0 until arr.length-1-i) {
        if (gt(arr(j), arr(j+1))) {
          val tmp = arr(j)
          arr(j) = arr(j+1)
          arr(j+1) = tmp
        }
      }
    }
  }

  val nums = Array.fill(10)(util.Random.nextInt(100))
  println(nums.mkString(" "))
  bubbleSort(nums)(_%10 >  _%10)
  println(nums.mkString(" "))

  val students = """
  Aidan C.
  Aidan M.
  Anh
  Aaron
  Jessica
  Martin
  Jason
  Matthew
  Emma
  Anthony
  Lucy
  Sara
  Liliana
  Douglas
  """.trim.split("\n").map(_.trim -> math.random)
  students.foreach(println)
  bubbleSort(students)(_._1 > _._1)
  println()
  students.foreach(println)
  bubbleSort(students)(_._2 > _._2)
  println()
  students.foreach(println)
}
