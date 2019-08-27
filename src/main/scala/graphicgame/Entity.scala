package graphicgame

class Entity (private var x: Double, private var y: Double) {
    val width = 1.0
    val height = 1.0

    def intersects(e: Entity): Boolean = {
        val intersectX = (x - e.x).abs < (width + e.width)/2
        val intersectY = (y - e.y).abs < (height + e.height)/2
        intersectX && intersectY
    }
}