// Exp 28: Null Safety - Nullable Types, Safe Calls and Elvis Operator

fun main() {
    // 1. Nullable vs non-nullable
    println("===== 1. NULLABLE vs NON-NULLABLE =====")
    var name: String = "Kotlin"       // non-nullable: cannot hold null
    var nickname: String? = "Kotty"   // nullable: can hold null
    println("name = $name")
    println("nickname = $nickname")

    // name = null    // Compile-time error: null cannot be a value of a non-null type
    nickname = null   // Allowed
    println("nickname after assigning null = $nickname")

    // 2. Safe call operator ?.
    println("\n===== 2. SAFE CALL OPERATOR (?.) =====")
    for (text in listOf("Hello", null)) {
        println("Value: $text")
        println("Length: ${text?.length}")
        println("Uppercase: ${text?.uppercase()}")
    }

    // 3. Elvis operator ?:
    println("\n===== 3. ELVIS OPERATOR (?:) =====")
    for (user in listOf("Rahul", null)) {
        val result = user ?: "Guest"
        val length = user?.length ?: 0
        println("Value: $user -> Result: $result, Length: $length")
    }

    // 4. Not-null assertion !! with try-catch
    println("\n===== 4. NOT-NULL ASSERTION (!!) =====")
    for (value in listOf("Android", null)) {
        try {
            println("Length: ${value!!.length}")
        } catch (e: NullPointerException) {
            println("Caught NullPointerException: cannot force-unwrap a null value")
        }
    }

    println("\nProgram completed successfully")
}