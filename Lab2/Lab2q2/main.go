package main

import "fmt"

func main() {

	s := []int{1, 2, 3, 4, 5}
	fmt.Println("Original slice:", s)

	var a int
	fmt.Print("Enter a number to add: ")
	fmt.Scan(&a)
	s = append(s, a)
	fmt.Println("Slice after add:", s)

	var removeIndex int
	fmt.Print("Enter index to remove: ")
	fmt.Scan(&removeIndex)
	s = append(s[:removeIndex], s[removeIndex+1:]...)
	fmt.Println("Slice after remove:", s)

	var updateIndex, newValue int
	fmt.Print("Enter index to update: ")
	fmt.Scan(&updateIndex)
	fmt.Print("Enter new value: ")
	fmt.Scan(&newValue)
	s[updateIndex] = newValue
	fmt.Println("Slice after update:", s)

	marks := map[string]int{"Math": 85, "Science": 90}
	fmt.Println("Original map:", marks)

	var subject string
	var subMarks int
	fmt.Print("Enter subject to insert: ")
	fmt.Scan(&subject)
	fmt.Print("Enter marks: ")
	fmt.Scan(&subMarks)
	marks[subject] = subMarks
	fmt.Println("Map after insert:", marks)

	var deleteKey string
	fmt.Print("Enter subject to delete: ")
	fmt.Scan(&deleteKey)
	delete(marks, deleteKey)
	fmt.Println("Map after delete:", marks)

	var lookupKey string
	fmt.Print("Enter subject to look up: ")
	fmt.Scan(&lookupKey)
	value, found := marks[lookupKey]
	fmt.Println("Lookup result:", value, found)
}
