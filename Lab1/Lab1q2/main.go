package main

import (
	"fmt"
	"strconv"
)

func main() {
	var s1, s2 string
	for {
		fmt.Print("Enter two numbers: ")
		fmt.Scan(&s1, &s2)
		x, e1 := strconv.ParseFloat(s1, 64)
		y, e2 := strconv.ParseFloat(s2, 64)
		if e1 != nil || e2 != nil {
			fmt.Println("Invalid input, try again.")
		} else if x < -1e6 || x > 1e6 || y < -1e6 || y > 1e6 {
			fmt.Println("Out of range (-1000000 to 1000000), try again.")
		} else {
			a, b := int(x), int(y)
			fmt.Println("Int:  ", a+b, a-b, a*b)
			fmt.Println("Float:", x+y, x-y, x*y)
			break
		}
	}
}
