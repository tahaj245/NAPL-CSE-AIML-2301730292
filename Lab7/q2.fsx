// Exp 13: Asynchronous and Parallel Workflows in F#
// Run with:  dotnet fsi AsyncParallel.fsx

open System
open System.Diagnostics
open System.Threading

// ---------- Helper: timestamped logging ----------
let sw = Stopwatch.StartNew()

let log (msg: string) =
    printfn "[%5d ms | thread %2d] %s" sw.ElapsedMilliseconds Thread.CurrentThread.ManagedThreadId msg

// ---------- Simulated tasks ----------

// I/O-bound task: simulates a delay (e.g. network / file / database call)
let simulateIO (name: string) (delayMs: int) (value: int) : Async<string> =
    async {
        log (sprintf "%s STARTED  (I/O-bound, will wait %d ms)" name delayMs)
        do! Async.Sleep delayMs
        log (sprintf "%s FINISHED" name)
        return sprintf "%s -> result = %d" name value
    }

// Compute-intensive task: sums squares from 1..n
let simulateCompute (name: string) (n: int) : Async<string> =
    async {
        log (sprintf "%s STARTED  (compute-intensive, n = %d)" name n)
        let total =
            [ 1L .. int64 n ]
            |> List.fold (fun acc x -> acc + x * x) 0L
        log (sprintf "%s FINISHED" name)
        return sprintf "%s -> sum of squares 1..%d = %d" name n total
    }

// ======================================================
// PART A: Sequential execution (for comparison)
// ======================================================
printfn "=========== SEQUENTIAL EXECUTION ==========="
sw.Restart()
let seqResults =
    [ simulateIO "Task-1" 3000 100
      simulateIO "Task-2" 1000 200
      simulateIO "Task-3" 2000 300 ]
    |> List.map Async.RunSynchronously
printfn "\nSequential results:"
seqResults |> List.iter (printfn "  %s")
printfn "Sequential total time: %d ms (approx. 3000 + 1000 + 2000)\n" sw.ElapsedMilliseconds

// ======================================================
// PART B: Parallel execution using Async.Parallel
// ======================================================
printfn "=========== PARALLEL: Async.Parallel ==========="
sw.Restart()
log "Launching 4 tasks concurrently..."

let parallelResults =
    [ simulateIO "Task-1" 3000 100
      simulateIO "Task-2" 1000 200
      simulateIO "Task-3" 2000 300
      simulateCompute "Task-4" 5000000 ]
    |> Async.Parallel
    |> Async.RunSynchronously

log "All tasks completed. Collecting results..."
printfn "\nAsync.Parallel results (in the order tasks were listed):"
parallelResults |> Array.iter (printfn "  %s")
printfn "Parallel total time: %d ms (approx. the longest task, ~3000 ms)\n" sw.ElapsedMilliseconds
printfn "Note: Task-2 finished BEFORE Task-1 even though Task-1 started first"
printfn "      -> execution was NON-SEQUENTIAL.\n"

// ======================================================
// PART C: Concurrent execution using Async.StartChild
// ======================================================
printfn "=========== CONCURRENT: Async.StartChild ==========="
sw.Restart()

let workflow =
    async {
        // Start each task as a child; control returns immediately
        let! childA = Async.StartChild (simulateIO "Child-A" 2500 10)
        let! childB = Async.StartChild (simulateIO "Child-B" 500 20)
        let! childC = Async.StartChild (simulateIO "Child-C" 1500 30)

        log "Parent: all children launched, parent is free to do other work"

        // Wait for each child's result
        let! a = childA
        let! b = childB
        let! c = childC
        return [ a; b; c ]
    }

let childResults = Async.RunSynchronously workflow
log "Parent: all children completed"

printfn "\nAsync.StartChild results:"
childResults |> List.iter (printfn "  %s")
printfn "StartChild total time: %d ms (approx. the longest child, ~2500 ms)" sw.ElapsedMilliseconds