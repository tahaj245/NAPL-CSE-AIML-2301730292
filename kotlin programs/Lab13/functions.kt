fun greet(name: String = "Student", greeting: String = "Hello") {
    println("$greeting, $name!")
}

fun createProfile(name: String, age: Int = 18, city: String = "Delhi") {
    println("Name: $name, Age: $age, City: $city")
}

fun printNumbers(vararg numbers: Int) {
    println("Received ${numbers.size} numbers:")
    for (n in numbers) {
        print("$n ")
    }
    println()
}


fun applyToList(list: List<Int>, operation: (Int) -> Int): List<Int> {
    val result = mutableListOf<Int>()
    for (item in list) {
        result.add(operation(item))
    }
    return result
}

fun main() {
    println("--- (1) Default parameters ---")
    greet()                         // both defaults used
    greet("Ravi")                   // only greeting default used
    greet("Asha", "Welcome")        // no defaults used

    println("\n--- (2) Named arguments ---")
    createProfile(name = "Ravi", age = 21, city = "Mumbai")
    createProfile(city = "Pune", name = "Asha", age = 20)   // out of order
    createProfile(name = "Kiran", city = "Chennai")         // age uses default

    println("\n--- (3) Vararg ---")
    printNumbers(1, 2, 3)
    printNumbers(10, 20, 30, 40, 50)
    printNumbers()                  // zero arguments is allowed

    println("\n--- (4) Higher-order function ---")
    val nums = listOf(1, 2, 3, 4, 5)

    val squares = applyToList(nums) { it * it }
    println("Squares: $squares")

    val doubled = applyToList(nums) { x -> x * 2 }
    println("Doubled: $doubled")

    val addTen: (Int) -> Int = { it + 10 }
    println("Plus 10: ${applyToList(nums, addTen)}")
}