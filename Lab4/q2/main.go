package main

import (
	"errors"
	"fmt"
	"math"
)

// Error 1: division by zero
func Divide(a, b float64) (float64, error) {
	if b == 0 {
		return 0, fmt.Errorf("cannot divide %v by zero", a)
	}
	return a / b, nil
}

// Error 2: negative square root
func SquareRoot(x float64) (float64, error) {
	if x < 0 {
		return 0, fmt.Errorf("cannot take square root of negative number: %v", x)
	}
	return math.Sqrt(x), nil
}

// Error 3: empty input
func Average(numbers []float64) (float64, error) {
	if len(numbers) == 0 {
		return 0, errors.New("input list is empty")
	}
	sum := 0.0
	for _, n := range numbers {
		sum += n
	}
	return sum / float64(len(numbers)), nil
}

func main() {
	// Valid inputs
	r1, _ := Divide(10, 2)
	fmt.Println("10 / 2 =", r1)

	r2, _ := SquareRoot(16)
	fmt.Println("sqrt(16) =", r2)

	r3, _ := Average([]float64{2, 4, 6})
	fmt.Println("average =", r3)

	// Error cases
	if _, err := Divide(10, 0); err != nil {
		fmt.Println("Error:", err)
	}
	if _, err := SquareRoot(-4); err != nil {
		fmt.Println("Error:", err)
	}
	if _, err := Average([]float64{}); err != nil {
		fmt.Println("Error:", err)
	}
}
