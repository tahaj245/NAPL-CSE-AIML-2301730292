package main

import (
	"fmt"

	"example.com/app/mathutil"
	"example.com/app/strop"
)

func main() {
	fmt.Println(mathutil.Add(5, 3))
	fmt.Println(mathutil.Pow(2, 3))
	fmt.Println(mathutil.Fact(5))
	fmt.Println(strop.CountVowels("Hello, World!"))
}
