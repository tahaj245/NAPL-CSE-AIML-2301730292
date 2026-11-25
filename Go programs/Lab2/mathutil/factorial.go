package mathutil

func Fact(a int) int {
	if a == 0 {
		return 1
	}
	return a * Fact(a-1)
}
