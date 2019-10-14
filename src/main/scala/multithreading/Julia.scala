package multithreading
import scalafx.stage.Stage
import scalafx.scene.image.WritableImage
import scalafx.scene.Scene
import scalafx.scene.image.ImageView
import scalafx.application.Platform
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import scala.compat.Platform

class Julia(c: Complex) {
  val stage = new Stage {
    title = "Julia at " + c
    scene = new Scene(800, 800) {
      val image = new WritableImage(800, 800)
      content = new ImageView(image)

      Platform.runLater { drawJulia(image) }
    }
  }
  stage.show()

  val MinReal = -1.5
  val MaxReal = 1.5
  val MinImag = -1.5
  val MaxImag = 1.5

  implicit val ec = ExecutionContext.global

  def drawJulia(img: WritableImage): Unit = {
    val writer = img.pixelWriter
    val start = System.nanoTime()
    val futures = for (j <- 0 until img.height().toInt) yield {
        val y = MinImag + j*(MaxImag-MinImag)/img.height()
        val colors = for (i <- 0 until img.width().toInt) yield {
          x = MinReal + i*(MaxReal-MinReal)/img.width()
          val cnt = juliaCount(Complex(x, y))
          Mandelbrot.mandelColor(cnt)
        }
        (j, colors)
      }
    }
    val f = Future.sequence(futures).map { rows =>    
      Platform.runLater {     // Platform.runLater won't be on midterm
        for((i, colors) <- rows) {
          for(j <- colors.indices) writer.setColor(i, j, colors(j))
        }
      }
    } // use this to get a future of sequence from a sequence of futures

    // important thing about this for the exam:
      // you can do things like mapping futures to make a new future (just like how mapping a list makes a new list)
      // use foreach to go from sequence of futures to future of sequence 
    f.foreach( _ => println(s"Drawing time: ${(System.nanoTime - start) * 1e-9} seconds"))

  def juliaCount(z0: Complex): Int = {
    var z = z0
    var cnt = 1
    while(cnt < Mandelbrot.MaxIters && z.magSqr < 4) {
      z = z*z + c
      cnt += 1
    }
    cnt
  }
}