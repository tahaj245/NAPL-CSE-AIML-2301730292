package main

import (
	"errors"
	"fmt"
)

type Item struct {
	ID       int
	Name     string
	Quantity int
	Price    float64
}

var items []Item // slice to store items

func find(id int) int {
	for i, it := range items {
		if it.ID == id {
			return i
		}
	}
	return -1
}

func check(it Item) error {
	if it.ID <= 0 {
		return errors.New("ID must be greater than 0")
	}
	if it.Name == "" {
		return errors.New("name cannot be empty")
	}
	if it.Quantity < 0 || it.Price < 0 {
		return errors.New("quantity and price cannot be negative")
	}
	return nil
}

func Add(it Item) error {
	if err := check(it); err != nil {
		return err
	}
	if find(it.ID) != -1 {
		return errors.New("item already exists")
	}
	items = append(items, it)
	return nil
}

func Update(it Item) error {
	if err := check(it); err != nil {
		return err
	}
	i := find(it.ID)
	if i == -1 {
		return errors.New("item not found")
	}
	items[i] = it
	return nil
}

func Remove(id int) error {
	i := find(id)
	if i == -1 {
		return errors.New("item not found")
	}
	items = append(items[:i], items[i+1:]...)
	return nil
}

// Runs in a goroutine, sends result through a channel
func Stats(list []Item, ch chan<- [2]float64) {
	total := 0.0
	for _, it := range list {
		total += float64(it.Quantity) * it.Price
	}
	ch <- [2]float64{total, float64(len(list))}
}

func main() {
	fmt.Println(Add(Item{1, "Pen", 10, 5}))
	fmt.Println(Add(Item{2, "Book", 3, 100}))
	fmt.Println(Add(Item{1, "Dup", 1, 1})) // error
	fmt.Println(Update(Item{2, "Notebook", 4, 90}))
	fmt.Println(Remove(1))
	fmt.Println(Remove(9)) // error

	ch := make(chan [2]float64)
	go Stats(items, ch)
	r := <-ch
	fmt.Printf("Total value: %.2f, Item count: %.0f\n", r[0], r[1])
}
