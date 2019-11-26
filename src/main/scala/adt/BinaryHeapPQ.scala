package adt
import scala.reflect.ClassTag

class BinaryHeapPQ[A: ClassTag](higherP: (A, A) => Boolean) extends MyPriorityQueue[A] {
    private var heap = Array.fill(10)(null.asInstanceOf[A])     // new Array[A](10)
    private var back = 1    
        // need to keep track of how many things are in the array (i.e. where you need to add the next thing)
        // starts at 1 because index 0 should be empty
    
    def dequeue(): A = {
        val ret = heap(1)
        back -= 1
        val stone = 1
        var flag = true     // surrogate for while stone has a child that is lower priority than it

        while (stone * 2 < back && flag) {  // while the stone has at least one child and flag is still true
            var betterChild = stone * 2     // start off assuming it's the left child of stone
            if (stone * 2 + 1 < back && higherP(heap(stone * 2 + 1), heap(betterChild))) betterChild += 1  
                // if there's a right child and it's higher priority than the left child
            if (higherP(heap(betterChild), heap(back))) {
                heap(stone) = heap(betterChild)     // overwrite head(stone) with heap(betterChild)
                stone = betterChild
            } else flag = false
        }

        heap(stone) = heap(back)    // heap(back) needs to be copied over to where the stone stopped moving
        ret
    }
    
    def enqueue(value: A): Unit = {
        if (back == heap.length) {
            heap = Array.tabulate(heap.length * 2)(i => if (i < heap.length) heap(i) else null.asInstanceOf[A])
        }
        var bubble = back

        // rise up if element is a higher priority than its parent
        while (bubble > 1 && higherP(value, heap(bubble/  2))) {    // while value is higher priority than bubble
            heap(bubble) = heap(bubble / 2)
            bubble /= 2     // means bubble = bubble/2
        }
        heap(bubble) = value
        back += 1
    }
    
    def peek(): A = heap(1)

    def isEmpty: Boolean = back == 1
}