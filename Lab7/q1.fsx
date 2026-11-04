// Exp 12: Higher-Order Functions in F# (List.map, List.filter, List.fold / List.reduce)
// All functions are passed as pure lambda expressions.
// Run with:  dotnet fsi HigherOrder.fsx

// ---------- Test lists ----------
let listA = [1; 2; 3; 4; 5; 6]
let listB = [10; 15; 20; 25; 30]
let listC = [-3; 0; 7; 8; -2]

// ---------- (1) List.map : square every element ----------
printfn "=== (1) List.map : square all integers ==="
let squaredA = List.map (fun x -> x * x) listA
let squaredB = List.map (fun x -> x * x) listB
let squaredC = List.map (fun x -> x * x) listC
printfn "Input : %A  ->  Squares: %A" listA squaredA
printfn "Input : %A  ->  Squares: %A" listB squaredB
printfn "Input : %A  ->  Squares: %A" listC squaredC

// ---------- (2) List.filter : retain only even numbers ----------
printfn "\n=== (2) List.filter : retain only even numbers ==="
let evenA = List.filter (fun x -> x % 2 = 0) listA
let evenB = List.filter (fun x -> x % 2 = 0) listB
let evenC = List.filter (fun x -> x % 2 = 0) listC
printfn "Input : %A  ->  Evens: %A" listA evenA
printfn "Input : %A  ->  Evens: %A" listB evenB
printfn "Input : %A  ->  Evens: %A" listC evenC

// ---------- (3) List.fold / List.reduce : aggregate ----------
printfn "\n=== (3) List.fold / List.reduce : aggregation ==="

// Sum using List.fold (initial accumulator = 0)
let sumA = List.fold (fun acc x -> acc + x) 0 listA
let sumB = List.fold (fun acc x -> acc + x) 0 listB

// Product using List.fold (initial accumulator = 1)
let prodA = List.fold (fun acc x -> acc * x) 1 listA
let prodC = List.fold (fun acc x -> acc * x) 1 [2; 3; 4]

// Maximum using List.reduce (no initial value needed)
let maxB = List.reduce (fun a b -> if a > b then a else b) listB
let maxC = List.reduce (fun a b -> if a > b then a else b) listC

printfn "Sum     of %A = %d" listA sumA
printfn "Sum     of %A = %d" listB sumB
printfn "Product of %A = %d" listA prodA
printfn "Product of %A = %d" [2; 3; 4] prodC
printfn "Maximum of %A = %d" listB maxB
printfn "Maximum of %A = %d" listC maxC

// ---------- (4) Combined pipeline (bonus) ----------
printfn "\n=== Bonus: filter -> map -> fold pipeline ==="
let result =
    listA
    |> List.filter (fun x -> x % 2 = 0)
    |> List.map (fun x -> x * x)
    |> List.fold (fun acc x -> acc + x) 0
printfn "Sum of squares of even numbers in %A = %d" listA result