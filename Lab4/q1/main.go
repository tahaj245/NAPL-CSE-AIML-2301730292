package main

import (
	"fmt"
	"time"
)

// A small box to carry a result through the channel
type Result struct {
	Name string
	Data []int
}

func squares(n int, ch chan Result) {
	fmt.Println("Squares goroutine started")
	time.Sleep(1 * time.Second) // pretend this takes time
	data := []int{}
	for i := 1; i <= n; i++ {
		data = append(data, i*i)
	}
	ch <- Result{"Squares", data} // send result into the channel
	fmt.Println("Squares goroutine finished")
}

func cubes(n int, ch chan Result) {
	fmt.Println("Cubes goroutine started")
	time.Sleep(2 * time.Second)
	data := []int{}
	for i := 1; i <= n; i++ {
		data = append(data, i*i*i)
	}
	ch <- Result{"Cubes", data}
	fmt.Println("Cubes goroutine finished")
}

func fibonacci(n int, ch chan Result) {
	fmt.Println("Fibonacci goroutine started")
	time.Sleep(500 * time.Millisecond)
	data := []int{}
	a, b := 0, 1
	for i := 0; i < n; i++ {
		data = append(data, a)
		a, b = b, a+b
	}
	ch <- Result{"Fibonacci", data}
	fmt.Println("Fibonacci goroutine finished")
}

func main() {
	ch := make(chan Result) // channel that carries Result values

	// Start 3 goroutines (they run at the same time)
	go squares(5, ch)
	go cubes(5, ch)
	go fibonacci(8, ch)

	// main waits and reads 3 results from the channel
	fmt.Println("\n--- Results in main ---")
	for i := 0; i < 3; i++ {
		r := <-ch
		fmt.Println(r.Name, ":", r.Data)
	}
}
