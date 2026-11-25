class Student(
    var rollNo: Int,                 
    var name: String,
    var course: String,
    percentage: Double
) {

    var percentage: Double = percentage
        private set

    
    private fun getGrade(): String = when {
        percentage >= 75 -> "Distinction"
        percentage >= 60 -> "First Class"
        percentage >= 50 -> "Second Class"
        percentage >= 40 -> "Pass"
        else -> "Fail"
    }

     
    fun updateData() {
        print("Enter roll number: ")
        rollNo = readLine()?.trim()?.toIntOrNull() ?: rollNo

        print("Enter name: ")
        val newName = readLine()?.trim()
        if (!newName.isNullOrEmpty()) name = newName

        print("Enter course: ")
        val newCourse = readLine()?.trim()
        if (!newCourse.isNullOrEmpty()) course = newCourse

        print("Enter percentage: ")
        percentage = readLine()?.trim()?.toDoubleOrNull() ?: percentage
    }


    fun displayDetails() {
        println("--------------------------------")
        println("Roll No    : $rollNo")
        println("Name       : $name")
        println("Course     : $course")
        println("Percentage : ${"%.2f".format(percentage)}%")
        println("Grade      : ${getGrade()}")
        println("--------------------------------")
    }
}

fun main() {

    val s1 = Student(101, "Ravi", "B.Tech", 72.5)
    println("Student 1 (initial details):")
    s1.displayDetails()

    println("\nUpdate Student 1:")
    s1.updateData()
    println("\nStudent 1 (updated details):")
    s1.displayDetails()

    
    val s2 = Student(102, "Asha", "BCA", 81.0)
    println("\nStudent 2 (initial details):")
    s2.displayDetails()

    println("\nUpdate Student 2:")
    s2.updateData()
    println("\nStudent 2 (updated details):")
    s2.displayDetails()
}