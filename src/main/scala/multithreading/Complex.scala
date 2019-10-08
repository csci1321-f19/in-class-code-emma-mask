package multithreading

case class Complex(r: Double, i: Double) {
  def +(that: Complex) = Complex(r + that.r, i + that.i)
  def -(that: Complex) = Complex(r - that.r, i - that.i)
  def *(that: Complex) = Complex(r*that.r-i*that.i, r*that.i + i*that.r)
  def *(x: Double) = Complex(r*x, i*x)
  def magSqr = r*r + i*i
}