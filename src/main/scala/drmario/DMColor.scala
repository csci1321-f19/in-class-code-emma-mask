package drmario

object DMColor extends Enumeration {
    val Red, Yellow, Blue = Value

    def randomColor(): DMColor.Value = util.Random.nextInt(3) match {
        case 0 => Red
        case 1 => Yellow
        case 2 => Blue
    }
}