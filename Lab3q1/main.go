package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

// Step 1: Person struct
type Person struct {
	Name   string
	Age    int
	Job    string
	Salary float64
}

func readLine(reader *bufio.Reader, prompt string) string {
	fmt.Print(prompt)
	text, _ := reader.ReadString('\n')
	return strings.TrimSpace(text)
}

func (p *Person) ReadData(reader *bufio.Reader) {
	p.Name = readLine(reader, "Enter name: ")

	age, err := strconv.Atoi(readLine(reader, "Enter age: "))
	if err != nil {
		fmt.Println("Invalid age, defaulting to 0")
	}
	p.Age = age

	p.Job = readLine(reader, "Enter job: ")

	salary, err := strconv.ParseFloat(readLine(reader, "Enter salary: "), 64)
	if err != nil {
		fmt.Println("Invalid salary, defaulting to 0")
	}
	p.Salary = salary
}

func (p Person) Display() {
	fmt.Println("------------------------")
	fmt.Printf("Name   : %s\n", p.Name)
	fmt.Printf("Age    : %d\n", p.Age)
	fmt.Printf("Job    : %s\n", p.Job)
	fmt.Printf("Salary : %.2f\n", p.Salary)
	fmt.Println("------------------------")
}

func main() {
	reader := bufio.NewReader(os.Stdin)

	var p1, p2 Person

	fmt.Println("Enter details for Person 1:")
	p1.ReadData(reader)

	fmt.Println("\nEnter details for Person 2:")
	p2.ReadData(reader)

	fmt.Println("\nPerson 1 details:")
	p1.Display()

	fmt.Println("\nPerson 2 details:")
	p2.Display()
}
