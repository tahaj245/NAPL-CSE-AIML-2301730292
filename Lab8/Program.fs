module Program

open System
open Microsoft.Data.Sqlite

type Student = { Id: int; Name: string; Age: int; Course: string }

let connString = "Data Source=students.db"

// READ
let readAll (conn: SqliteConnection) : Student list =
    use cmd = conn.CreateCommand()
    cmd.CommandText <- "SELECT Id, Name, Age, Course FROM Students ORDER BY Id"
    use reader = cmd.ExecuteReader()
    [ while reader.Read() do
        yield { Id = reader.GetInt32 0
                Name = reader.GetString 1
                Age = reader.GetInt32 2
                Course = reader.GetString 3 } ]

let printState (conn: SqliteConnection) (title: string) =
    printfn "\n=== %s ===" title
    match readAll conn with
    | [] -> printfn "(no records)"
    | students ->
        students
        |> List.iter (fun s -> printfn "Id=%d | Name=%s | Age=%d | Course=%s" s.Id s.Name s.Age s.Course)

// CREATE
let createTable (conn: SqliteConnection) =
    use cmd = conn.CreateCommand()
    cmd.CommandText <-
        """CREATE TABLE IF NOT EXISTS Students (
             Id INTEGER PRIMARY KEY AUTOINCREMENT,
             Name TEXT NOT NULL,
             Age INTEGER NOT NULL,
             Course TEXT NOT NULL)"""
    cmd.ExecuteNonQuery() |> ignore

let insertStudent (conn: SqliteConnection) (name: string) (age: int) (course: string) =
    use cmd = conn.CreateCommand()
    cmd.CommandText <- "INSERT INTO Students (Name, Age, Course) VALUES ($name, $age, $course)"
    cmd.Parameters.AddWithValue("$name", name) |> ignore
    cmd.Parameters.AddWithValue("$age", age) |> ignore
    cmd.Parameters.AddWithValue("$course", course) |> ignore
    cmd.ExecuteNonQuery() |> ignore

// UPDATE
let updateStudent (conn: SqliteConnection) (name: string) (newAge: int) (newCourse: string) =
    use cmd = conn.CreateCommand()
    cmd.CommandText <- "UPDATE Students SET Age = $age, Course = $course WHERE Name = $name"
    cmd.Parameters.AddWithValue("$age", newAge) |> ignore
    cmd.Parameters.AddWithValue("$course", newCourse) |> ignore
    cmd.Parameters.AddWithValue("$name", name) |> ignore
    cmd.ExecuteNonQuery() |> ignore

// DELETE
let deleteStudent (conn: SqliteConnection) (name: string) =
    use cmd = conn.CreateCommand()
    cmd.CommandText <- "DELETE FROM Students WHERE Name = $name"
    cmd.Parameters.AddWithValue("$name", name) |> ignore
    cmd.ExecuteNonQuery() |> ignore

[<EntryPoint>]
let main _ =
    use conn = new SqliteConnection(connString)
    conn.Open()

    createTable conn
    use reset = conn.CreateCommand()
    reset.CommandText <- "DELETE FROM Students"   // start clean on each run
    reset.ExecuteNonQuery() |> ignore

    insertStudent conn "Asha" 20 "Computer Science"
    insertStudent conn "Ravi" 21 "Mathematics"
    printState conn "After CREATE (INSERT)"

    printState conn "After READ (SELECT)"

    updateStudent conn "Asha" 22 "Data Science"
    printState conn "After UPDATE"

    deleteStudent conn "Ravi"
    printState conn "After DELETE"

    0