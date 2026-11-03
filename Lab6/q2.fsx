let isPrime (n: int) : bool =
    // Recursively test divisors d = 2, 3, ... while d*d <= n
    let rec noDivisor d =
        if d * d > n then true
        elif n % d = 0 then false
        else noDivisor (d + 1)
    n > 1 && noDivisor 2

// Three sets of test inputs
let testSets =
    [ "Set 1 (small numbers)",   [ 0; 1; 2; 3; 4; 5 ]
      "Set 2 (mixed)",           [ 17; 18; 29; 35; 49; 97 ]
      "Set 3 (larger/negative)", [ -7; 100; 101; 997; 1000; 7919 ] ]

for (name, inputs) in testSets do
    printfn "%s" name
    inputs
    |> List.map (fun n -> n, isPrime n)
    |> List.iter (fun (n, p) ->
        printfn "  %5d -> %s" n (if p then "Prime" else "Not prime"))
    printfn ""