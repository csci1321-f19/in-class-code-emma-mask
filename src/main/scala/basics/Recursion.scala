package basics

object Recursion extends App {
    def factorial(n: Int): Int = if (n < 2) 1 else n * factorial(n-1)

    def fib(n: Int): Int = if (n < 2) 1 else fib(n-2) + fib(n-1)

    def packBins(bins: Array[Double], objs: Array[Double]): Boolean = {
        // order 2^n
        def helper(o: Int): Boolean = {
            // o is index into objs
            if (o >= objs.length) true
            else {
                var ret = false
                for (b <- bins.indices) {
                    if(objs(o) <= bins(b)) {
                        bins(b) -= objs(o)
                        ret ||= helper(o + 1)   // ||= short for ret = ret || helper
                                                // || is a short circut operator, so if ret is true, it doesn't bother calling helper(o + 1)
                        bins(b) += objs(o)
                    }
                }
                ret
            }
        }
        helper(0)
    }

    def knapsack(values: List[Double], weights: List[Double], weightLeft: Double): Double = {
        // order 
        if (values.isEmpty) 0.0
        else {
            if (weights.head <= weightLeft) {
                knapsack(values.tail, weights.tail, weightLeft) max
                    values.head + knapsack(values.tail, weights.tail, weightLeft - weights.head)
            } else {
                knapsack(values.tail, weights.tail, weightLeft)
            }
        }
    }
}