open System

// Pattern matching with guards to classify the division
let classify (percentage: float) : string =
    match percentage with
    | p when p >= 60.0 -> "First Division"
    | p when p >= 50.0 -> "Second Division"
    | p when p >= 40.0 -> "Third Division"
    | _ -> "Fail"

// Read one valid mark (0-100), asking again on bad input
let rec readMark (subject: int) : float =
    printf "Enter marks for subject %d (out of 100): " subject
    match Double.TryParse(Console.ReadLine()) with
    | true, m when m >= 0.0 && m <= 100.0 -> m
    | _ ->
        printfn "Invalid input. Enter a number between 0 and 100."
        readMark subject

let percentageOf (marks: float list) : float =
    List.sum marks / (float marks.Length * 100.0) * 100.0

let marks = [ 1 .. 4 ] |> List.map readMark
let pct = percentageOf marks

printfn "\nTotal marks : %.2f / %.0f" (List.sum marks) (float marks.Length * 100.0)
printfn "Percentage  : %.2f%%" pct
printfn "Division    : %s" (classify pct)