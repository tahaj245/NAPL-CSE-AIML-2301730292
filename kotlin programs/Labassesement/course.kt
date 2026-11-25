data class Student(
    val id: Int,
    val name: String,
    val email: String? = null,   
    val major: String? = null
)

data class Course(
    val code: String,
    val title: String,
    val credits: Int,
    val instructor: String? = null,
    val capacity: Int = 30
)

data class Enrollment(
    val student: Student,
    val course: Course,
    val grade: Double? = null    
)

class Registry(
    val students: List<Student>,
    val courses: List<Course>,
    val enrollments: List<Enrollment>
)

fun Student.displayName(): String = "$name (${major ?: "Undeclared"})"

fun Double?.toLetterGrade(): String = when {
    this == null -> "N/A"
    this >= 90 -> "A"
    this >= 80 -> "B"
    this >= 70 -> "C"
    this >= 60 -> "D"
    else -> "F"
}


fun List<Enrollment>.averageGrade(): Double? =
    mapNotNull { it.grade }.takeIf { it.isNotEmpty() }?.average()


fun Student.contactInfo(): String = email?.let { "$name <$it>" } ?: "$name (no email)"


@DslMarker
annotation class RegistryDsl


@RegistryDsl
class CourseBuilder(private val code: String, private val title: String, private val credits: Int) {
    var instructor: String? = null
    var capacity: Int = 30
    fun build() = Course(code, title, credits, instructor, capacity)
}

@RegistryDsl
class StudentBuilder(private val id: Int, private val name: String) {
    var email: String? = null
    var major: String? = null
    fun build() = Student(id, name, email, major)
}

@RegistryDsl
class RegistryBuilder {
    private val courses = mutableMapOf<String, Course>()
    private val students = mutableMapOf<Int, Student>()
    private val enrollments = mutableListOf<Enrollment>()

    fun course(code: String, title: String, credits: Int, init: CourseBuilder.() -> Unit = {}) {
        courses[code] = CourseBuilder(code, title, credits).apply(init).build()
    }

    fun student(id: Int, name: String, init: StudentBuilder.() -> Unit = {}) {
        students[id] = StudentBuilder(id, name).apply(init).build()
    }

    fun enroll(studentId: Int, courseCode: String, grade: Double? = null) {
        val student = students[studentId] ?: error("Unknown student id: $studentId")
        val course = courses[courseCode] ?: error("Unknown course code: $courseCode")
        val current = enrollments.count { it.course.code == courseCode }
        require(current < course.capacity) { "Course $courseCode is full" }
        enrollments += Enrollment(student, course, grade)
    }

    fun build() = Registry(students.values.toList(), courses.values.toList(), enrollments.toList())
}

fun registry(init: RegistryBuilder.() -> Unit): Registry =
    RegistryBuilder().apply(init).build()


@RegistryDsl
class SectionBuilder {
    val lines = mutableListOf<String>()
    fun line(text: String) { lines += text }
    fun bullet(text: String) { lines += "  - $text" }
}

@RegistryDsl
class ReportBuilder {
    private val sb = StringBuilder()

    fun title(text: String) {
        sb.appendLine("=".repeat(50))
        sb.appendLine(text.uppercase())
        sb.appendLine("=".repeat(50))
    }

    fun section(name: String, init: SectionBuilder.() -> Unit) {
        sb.appendLine()
        sb.appendLine("[$name]")
        SectionBuilder().apply(init).lines.forEach { sb.appendLine(it) }
    }

    fun build(): String = sb.toString()
}

fun report(init: ReportBuilder.() -> Unit): String =
    ReportBuilder().apply(init).build()



fun main() {
    
    val reg = registry {
        course("CS101", "Intro to Programming", 4) {
            instructor = "Dr. Sharma"
            capacity = 3
        }
        course("CS201", "Data Structures", 4) { instructor = "Dr. Rao" }
        course("MA101", "Calculus", 3)   

        student(1, "Sameer") { email = "sameer@uni.edu"; major = "Computer Science" }
        student(2, "Aaditya") { major = "Mathematics" }
        student(3, "Zoya") { email = "zoya@uni.edu" }   // no major
        student(4, "Kabeer") { email = "kabeer@uni.edu"; major = "Computer Science" }

        enroll(1, "CS101", 92.0)
        enroll(1, "CS201", 85.5)
        enroll(2, "CS101", 58.0)
        enroll(2, "MA101", 74.0)
        enroll(3, "CS101", 66.0)
        enroll(3, "MA101")            
        enroll(4, "CS201", 91.0)
    }

   

    
    val studentNames = reg.students.map { it.displayName() }

    
    val passed = reg.enrollments.filter { (it.grade ?: 0.0) >= 60.0 }
    val failedOrPending = reg.enrollments.filterNot { (it.grade ?: 0.0) >= 60.0 }

    
    val byCourse = reg.enrollments.groupBy { it.course.code }
    val byStudent = reg.enrollments.groupBy { it.student }
    val studentsByMajor = reg.students.groupBy { it.major ?: "Undeclared" }

    
    val allGrades = reg.enrollments.mapNotNull { it.grade }
    val totalPoints = if (allGrades.isNotEmpty()) allGrades.reduce { a, b -> a + b } else 0.0

    
    val ranking = byStudent
        .mapNotNull { (student, list) -> list.averageGrade()?.let { student to it } }
        .sortedByDescending { it.second }

    
    val output = report {
        title("Course Registration Report")

        section("Students") {
            studentNames.forEach { bullet(it) }
        }

        section("Contact List") {
            reg.students.forEach { bullet(it.contactInfo()) }
        }

        section("Enrollment per Course") {
            byCourse.forEach { (code, list) ->
                val course = list.first().course
                line("$code - ${course.title} | Instructor: ${course.instructor ?: "TBA"}")
                line("  Enrolled: ${list.size}/${course.capacity} | Avg: " +
                        (list.averageGrade()?.let { "%.1f".format(it) } ?: "N/A"))
            }
        }

        section("Students by Major") {
            studentsByMajor.forEach { (major, list) ->
                line("$major: ${list.joinToString { it.name }}")
            }
        }

        section("Ranking (by average grade)") {
            ranking.forEachIndexed { i, (student, avg) ->
                line("${i + 1}. ${student.name} - %.1f (${avg.toLetterGrade()})".format(avg))
            }
        }

        section("Summary") {
            line("Total enrollments : ${reg.enrollments.size}")
            line("Passed            : ${passed.size}")
            line("Failed / pending  : ${failedOrPending.size}")
            line("Sum of all grades : $totalPoints")
        }
    }

    println(output)
}