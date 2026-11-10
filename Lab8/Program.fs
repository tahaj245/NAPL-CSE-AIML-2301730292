module Program

open System
open MongoDB.Bson
open MongoDB.Driver

let connString = "mongodb://localhost:27017"

let printState (col: IMongoCollection<BsonDocument>) (title: string) =
    printfn "\n=== %s ===" title
    let docs = col.Find(FilterDefinition<BsonDocument>.Empty).ToList()
    if docs.Count = 0 then
        printfn "(no documents)"
    else
        for d in docs do
            printfn "%s" (d.ToJson())

[<EntryPoint>]
let main _ =
    let client = MongoClient(connString)
    let db = client.GetDatabase("college")
    let col = db.GetCollection<BsonDocument>("students")

    col.DeleteMany(FilterDefinition<BsonDocument>.Empty) |> ignore  // start clean

    // CREATE
    let asha =
        BsonDocument([ BsonElement("name", BsonString "Asha")
                       BsonElement("age", BsonInt32 20)
                       BsonElement("course", BsonString "Computer Science") ])
    let ravi =
        BsonDocument([ BsonElement("name", BsonString "Ravi")
                       BsonElement("age", BsonInt32 21)
                       BsonElement("course", BsonString "Mathematics") ])
    col.InsertOne(asha)
    col.InsertMany([ ravi ])
    printState col "After CREATE (insert)"

    // READ
    let filter = Builders<BsonDocument>.Filter.Eq("name", "Asha")
    let found = col.Find(filter).FirstOrDefault()
    printfn "\n=== READ (find name = Asha) ==="
    printfn "%s" (found.ToJson())

    // UPDATE
    let update =
        Builders<BsonDocument>.Update
            .Set("age", 22)
            .Set("course", "Data Science")
    col.UpdateOne(filter, update) |> ignore
    printState col "After UPDATE"

    // DELETE
    let delFilter = Builders<BsonDocument>.Filter.Eq("name", "Ravi")
    col.DeleteOne(delFilter) |> ignore
    printState col "After DELETE"

    0