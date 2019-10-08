package multithreading

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.image.Image
import scalafx.scene.image.ImageView
import scalafx.scene.image.WritableImage
import scalafx.scene.paint.Color
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scalafx.application.Platform
import scalafx.scene.input.MouseEvent

object Mandelbrot extends JFXApp {
  val MaxIters = 10000
  val MinReal = -1.5
  val MaxReal = 0.5
  val MinImag = -1.0
  val MaxImag = 1.0

  stage = new JFXApp.PrimaryStage {
    title = "Fun with Fractals"
    scene = new Scene(800, 800) {
      val image = new WritableImage(800, 800)
      val imageView = new ImageView(image)
      content = imageView

      onMouseClicked = (me: MouseEvent) => {
        val x = MinReal + me.x * (MaxReal - MinReal) / image.width()
        val y = MinImag + me.y * (MaxImag - MinImag) / image.height()
        new Julia(Complex(x, y))
      }

      Platform.runLater { drawMandelbrot(image) }
    }
  }

  def drawMandelbrot(img: WritableImage): Unit = {
    val writer = img.pixelWriter
    val start = System.nanoTime()
    for {
      j <- (0 until img.height().toInt).par
      y = MinImag + j*(MaxImag-MinImag)/img.height()
      i <- 0 until img.width().toInt
      x = MinReal + i*(MaxReal-MinReal)/img.width()
    } {
      val cnt = mandelCount(Complex(x, y))
      writer.setColor(i, j, mandelColor(cnt))
    }
    println(s"Drawing time: ${(System.nanoTime - start) * 1e-9} seconds")
  }

  def mandelColor(count: Int): Color = {
    if (count == MaxIters) Color.Black
    else {
      val cfrac = math.log(count.toDouble)/math.log(MaxIters)
      Color(cfrac, 0.0, 1.0 - cfrac, 1.0)
    }
  }

  def mandelCount(c: Complex): Int = {
    var z = c
    var cnt = 0
    while(cnt < MaxIters && z.magSqr < 4) {
      z = z*z + c
      cnt += 1
    }
    cnt
  }
}