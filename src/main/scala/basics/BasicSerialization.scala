package basics
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.io.ObjectInputStream
import java.io.FileInputStream

case class Student(first: String, last: String, grades: Array[Int])

object BasicSerialization extends App {
  val students = Array(Student("Jane", "Doe", Array(100, 100, 99)),
                       Student("John", "Doe", Array(56, 34, 12)))

  //writeToFile(students)
  println(readFromFile().foreach(println))

  def writeToFile(s: Array[Student]): Unit = {
    val oos = new ObjectOutputStream(new FileOutputStream("students.bin"))
    oos.writeObject(s)
    oos.close()
  }

  def readFromFile(): Array[Student] = {
    val ois = new ObjectInputStream(new FileInputStream("students.bin"))
    val ret = ois.readObject() match {
      case as: Array[Student] => as
    }
    ois.close()
    ret
  }
}