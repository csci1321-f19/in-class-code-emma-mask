package basics
import java.io.FileOutputStream
import java.io.ObjectOutputStream

case class Student(first: String, last: String, grades: Array[Int])

object BasicSerialization extends App {
    val students = Array(Student("Jane", "Doe", Array(100, 100, 99)),
                         Student("John", "Doe", Array(56, 34, 12)))

    writeToFile(students)

    def writeToFile(s: Array[Student]): Unit = {
        val oos = new ObjectOutputStream(new FileOutputStream("students.bin"))
        oos.writeObject(s)
        oos.close()
    }

    def readFromFile
}