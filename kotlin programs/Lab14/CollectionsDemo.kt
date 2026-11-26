

data class Student(val name: String, val branch: String, val percentage: Double)

fun demonstrateCollections() {
    println("===== PART 1: COLLECTIONS =====")


    val readOnlyList: List<String> = listOf("Apple", "Banana", "Mango")
    println("\nRead-only List: $readOnlyList")
    println("Contains Banana? ${readOnlyList.contains("Banana")}")
    println("Get index 2: ${readOnlyList[2]}")

    
    val mutableList = mutableListOf("Apple", "Banana", "Mango")
    println("\nMutable List: $mutableList")
    mutableList.add("Orange")
    println("After add: $mutableList")
    mutableList.remove("Banana")
    println("After remove: $mutableList")
    println("Contains Mango? ${mutableList.contains("Mango")}")
    println("Get index 0: ${mutableList[0]}")

    
    val readOnlySet: Set<Int> = setOf(10, 20, 30, 20)
    println("\nRead-only Set: $readOnlySet")
    println("Contains 20? ${readOnlySet.contains(20)}")
    println("Get element at 1: ${readOnlySet.elementAt(1)}")

 
    val mutableSet = mutableSetOf(10, 20, 30)
    println("\nMutable Set: $mutableSet")
    mutableSet.add(40)
    println("After adding 40: $mutableSet")
    println("Was duplicate 10 added? ${mutableSet.add(10)}")
    mutableSet.remove(20)
    println("After removing 20: $mutableSet")
    println("Contains 30? ${mutableSet.contains(30)}")
    println("Get element at 0: ${mutableSet.elementAt(0)}")

    
    val readOnlyMap: Map<Int, String> = mapOf(1 to "One", 2 to "Two", 3 to "Three")
    println("\nRead-only Map: $readOnlyMap")
    println("Contains key 2? ${readOnlyMap.containsKey(2)}")
    println("Contains value Three? ${readOnlyMap.containsValue("Three")}")
    println("Get key 1: ${readOnlyMap[1]}")

   
    val mutableMap = mutableMapOf(1 to "One", 2 to "Two")
    println("\nMutable Map: $mutableMap")
    mutableMap[3] = "Three"
    println("After add: $mutableMap")
    mutableMap.remove(1)
    println("After remove: $mutableMap")
    println("Contains key 3? ${mutableMap.containsKey(3)}")
    println("Get key 2: ${mutableMap[2]}")
}


fun demonstrateTransformations() {
    println("\n===== PART 2: MAP, FILTER, REDUCE =====")

    val numbers = listOf(1, 2, 3, 4, 5, 6)
    println("Original list: $numbers")

    val squares = numbers.map { it * it }
    println("After map (square): $squares")

    val evenSquares = squares.filter { it % 2 == 0 }
    println("After filter (even only): $evenSquares")

    val sum = evenSquares.reduce { total, value -> total + value }
    println("After reduce (sum): $sum")
}

fun demonstrateSortingAndGrouping() {
    println("\n===== PART 3: SORTING AND GROUPING =====")

    val students = listOf(
        Student("Aarav", "CSE", 82.5),
        Student("Diya", "ECE", 91.0),
        Student("Rohan", "CSE", 74.8),
        Student("Sneha", "ME", 88.2),
        Student("Kabir", "ECE", 67.4)
    )

    println("\nOriginal list:")
    students.forEach { println(it) }

    println("\nSorted by percentage (ascending):")
    students.sortedBy { it.percentage }.forEach { println(it) }

    println("\nSorted by percentage (descending):")
    students.sortedByDescending { it.percentage }.forEach { println(it) }

    println("\nGrouped by branch:")
    students.groupBy { it.branch }.forEach { (branch, group) ->
        println("$branch: ${group.map { it.name }}")
    }

    println("\nGrouped by result:")
    students.groupBy { if (it.percentage >= 75) "Distinction" else "Below Distinction" }
        .forEach { (result, group) ->
            println("$result: ${group.map { it.name }}")
        }
}

fun main() {
    demonstrateCollections()
    demonstrateTransformations()
    demonstrateSortingAndGrouping()
}