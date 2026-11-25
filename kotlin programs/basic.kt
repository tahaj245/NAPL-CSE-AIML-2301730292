fun helloKotlin() {
    println("Hello Kotlin!!!")
}


fun calculateBonus(deptCode: String, years: Int, salary: Double): Double {
    val percent = when (deptCode.uppercase()) {
        "IT" -> if (years > 5) 20 else 10
        "HR" -> if (years > 3) 15 else 10
        "FIN" -> if (years > 4) 18 else 10
        else -> 10
    }
    return salary * percent / 100
}

fun employeeBonus() {
    print("Enter department code (IT / HR / FIN / other): ")
    val dept = readLine()?.trim() ?: ""

    print("Enter years of experience: ")
    val years = readLine()?.trim()?.toIntOrNull() ?: 0

    print("Enter salary: ")
    val salary = readLine()?.trim()?.toDoubleOrNull() ?: 0.0

    val bonus = calculateBonus(dept, years, salary)
    println("Bonus amount for $dept with $years years of experience: $bonus")
}


fun digitAverage() {
    print("Enter an integer: ")
    val n = readLine()?.trim()?.toIntOrNull()

    if (n == null) {
        println("Invalid input")
        return
    }

    var num = Math.abs(n)
    var sum = 0
    var count = 0

    if (num == 0) {
        count = 1
    }
    while (num > 0) {
        sum += num % 10
        num /= 10
        count++
    }

    val average = sum.toDouble() / count
    println("Sum of digits = $sum, Number of digits = $count")
    println("Average of digits = $average")
}

fun main() {
    println("--- Task 1 ---")
    helloKotlin()

    println("\n--- Task 2 ---")
    employeeBonus()

    println("\n--- Task 3 ---")
    digitAverage()
}