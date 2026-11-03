open System
open System.Text.RegularExpressions

// (1) Reverse a string and check palindrome
let reverse (s: string) : string =
    s |> Seq.rev |> Seq.toArray |> String

let isPalindrome (s: string) : bool =
    let cleaned =
        s |> Seq.filter Char.IsLetterOrDigit
          |> Seq.map Char.ToLower
          |> Seq.toArray
          |> String
    cleaned = reverse cleaned

// (2) Transform using pattern matching
let transform (s: string) : string =
    match s with
    | "" -> s
    | _ when s |> Seq.forall Char.IsLetter -> s.ToUpper()   // all letters -> uppercase
    | _ when s |> Seq.forall Char.IsDigit  -> s + s         // all digits  -> doubled
    | _ -> s                                                // mixed       -> unchanged

// (3) Email validation using a Regex active pattern
let (|Email|_|) (input: string) =
    let pattern = @"^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\.[A-Za-z0-9-]+)*\.[A-Za-z]{2,}$"
    if Regex.IsMatch(input, pattern) then Some input else None

let validateEmail (s: string) : string =
    match s with
    | Email _ -> "Valid"
    | _ -> "Invalid"

// (4) Demonstrations
printfn "=== (1) Reverse & Palindrome ==="
for s in [ "hello"; "madam"; "A man, a plan, a canal: Panama"; "racecar"; "F# rocks" ] do
    printfn "  \"%s\" -> reversed: \"%s\", palindrome: %b" s (reverse s) (isPalindrome s)

printfn "\n=== (2) Transform ==="
for s in [ "hello"; "12345"; "abc123"; "FSharp"; "2026" ] do
    printfn "  \"%s\" -> \"%s\"" s (transform s)

printfn "\n=== (3) Email validation ==="
for e in [ "user@example.com"; "john.doe@mail.co.in"; "bad@email"; "no-at-sign.com"; "a@b.c" ] do
    printfn "  %-22s -> %s" e (validateEmail e)