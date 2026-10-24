package main

import "testing"

// ----- Valid input tests -----
func TestDivideValid(t *testing.T) {
	got, err := Divide(10, 2)
	if err != nil || got != 5 {
		t.Errorf("expected 5, got %v (error: %v)", got, err)
	}
}

func TestSquareRootValid(t *testing.T) {
	got, err := SquareRoot(16)
	if err != nil || got != 4 {
		t.Errorf("expected 4, got %v (error: %v)", got, err)
	}
}

func TestAverageValid(t *testing.T) {
	got, err := Average([]float64{2, 4, 6})
	if err != nil || got != 4 {
		t.Errorf("expected 4, got %v (error: %v)", got, err)
	}
}

// ----- Error case tests -----
func TestDivideByZero(t *testing.T) {
	_, err := Divide(5, 0)
	if err == nil {
		t.Error("expected an error for division by zero")
	}
}

func TestSquareRootNegative(t *testing.T) {
	_, err := SquareRoot(-9)
	if err == nil {
		t.Error("expected an error for negative square root")
	}
}

func TestAverageEmpty(t *testing.T) {
	_, err := Average([]float64{})
	if err == nil {
		t.Error("expected an error for empty input")
	}
}
