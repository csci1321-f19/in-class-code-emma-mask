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

  def quickSort1[A](lst: List[A])(lt: (A, A) => Boolean): List[A] = lst match {
    // the first way we're writing this is really bad
    case Nil => Nil
    case h :: Nil => lst
    case pivot :: t => 
      val (less, notLess) = t.partition(a => lt(a, pivot))
      quickSort1(less)(lt) ++ (pivot :: quickSort1(notLess)(lt))
  }

  def quickSort[A](arr: Array[A])(lt: (A, A) => Boolean): Unit = {
    def helper(start: Int, end: Int): Unit = {
      if(end - start >= 2) {
        // random pivot gets swapped to the start
        val r = util.Random.nextInt(end - start) + start
        val tmp0 = arr(r)
        arr(r) = arr(start)
        arr(start) = tmp0

        var low = start + 1  // where small elements go
        var high = end - 1    
        while (high >= low) { // 
          if(lt(arr(low) < arr(start))) {
            low += 1
          } else {
            val tmp = arr(low)
            arr(low) = arr(high)
            arr(high) = tmp
            high -= 1
          }
          // high ends up pointing to the last thing greater than the pivot
        }

        // swap pivot into place
        val tmp1 = arr(start)
        arr(start) = arr(high)
        arr(high) = tmp1

        helper(start, high)
        helper(high + 1, end)
      }
    }
    helper(0, arr.length)
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


  //println(quickSort1(List.fill(20)(util.Random.nextInt(100))(_ < _)))

  val nums2 = Array.fill(20)(util.Random.nextInt(100))
  quickSort(nums2)(_ < _)
  println(nums2.mkString(", "))
  println("quicksort done")
}