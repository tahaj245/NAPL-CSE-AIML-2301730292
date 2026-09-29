package main

import "fmt"

type Student struct {
	Name  string
	Age   int
	Marks float64
}

func increment(p *int) {
	*p = *p + 10
}

func main() {

	x := 25
	ptr := &x

	fmt.Println("=== Part 1: Referencing & Dereferencing ===")
	fmt.Println("Value of x           :", x)
	fmt.Println("Address of x (&x)    :", &x)
	fmt.Println("Pointer ptr (address):", ptr)
	fmt.Println("Value via *ptr       :", *ptr)

	*ptr = 50
	fmt.Println("x after *ptr = 50    :", x)

	num := 100
	fmt.Println("\n=== Part 2: Pass by Reference ===")
	fmt.Println("Before function call:", num)
	increment(&num)
	fmt.Println("After function call :", num)

	fmt.Println("\n=== Part 3: Struct using new() ===")
	s := new(Student) // allocates zeroed Student, returns *Student
	fmt.Println("Initial values      :", *s)

	s.Name = "Rahul"
	s.Age = 20
	s.Marks = 88.5
	fmt.Println("After modification  :", *s)

	(*s).Age = 21
	fmt.Printf("Name: %s, Age: %d, Marks: %.1f\n", s.Name, s.Age, s.Marks)
}
