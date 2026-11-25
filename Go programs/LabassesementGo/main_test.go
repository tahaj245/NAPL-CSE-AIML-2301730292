package main

import "testing"

func TestAdd(t *testing.T) {
	items = nil
	if err := Add(Item{1, "Pen", 10, 5}); err != nil {
		t.Fatal(err)
	}
	if Add(Item{1, "Pen", 10, 5}) == nil {
		t.Fatal("duplicate should fail")
	}
	if Add(Item{2, "", 1, 1}) == nil {
		t.Fatal("empty name should fail")
	}
}

func TestUpdateRemove(t *testing.T) {
	items = nil
	Add(Item{1, "Pen", 10, 5})
	if err := Update(Item{1, "Pencil", 2, 3}); err != nil || items[0].Name != "Pencil" {
		t.Fatal("update failed")
	}
	if Update(Item{9, "X", 1, 1}) == nil {
		t.Fatal("missing item should fail")
	}
	if err := Remove(1); err != nil || len(items) != 0 {
		t.Fatal("remove failed")
	}
}

func TestStats(t *testing.T) {
	list := []Item{{1, "A", 2, 10}, {2, "B", 1, 5}}
	ch := make(chan [2]float64)
	go Stats(list, ch)
	r := <-ch
	if r[0] != 25 || r[1] != 2 {
		t.Fatalf("got %v", r)
	}
}
