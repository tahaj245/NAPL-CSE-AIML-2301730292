open System

// (1) Base class with a virtual method
type Shape(name: string) =
    member this.Name = name

    // virtual method: abstract declaration + default implementation
    abstract Area : unit -> float
    default this.Area() = 0.0

    abstract Describe : unit -> string
    default this.Describe() =
        sprintf "%s -> Area = %.2f" this.Name (this.Area())

// (2) Derived classes overriding the base method
type Circle(radius: float) =
    inherit Shape("Circle")
    member this.Radius = radius
    override this.Area() = Math.PI * radius * radius

type Rectangle(width: float, height: float) =
    inherit Shape("Rectangle")
    member this.Width = width
    member this.Height = height
    override this.Area() = width * height

type Triangle(b: float, h: float) =
    inherit Shape("Triangle")
    override this.Area() = 0.5 * b * h

// (3) Instantiate objects and demonstrate polymorphism
let circle = Circle(5.0)
let rect = Rectangle(4.0, 6.0)
let tri = Triangle(3.0, 8.0)

printfn "--- Calling methods on individual objects ---"
printfn "Circle (r=5)         : %.2f" (circle.Area())
printfn "Rectangle (4 x 6)    : %.2f" (rect.Area())
printfn "Triangle (b=3, h=8)  : %.2f" (tri.Area())

printfn ""
printfn "--- Polymorphism: list of base-class references ---"
let shapes : Shape list =
    [ Shape("Generic Shape")
      circle :> Shape
      rect :> Shape
      tri :> Shape ]

for s in shapes do
    printfn "%s" (s.Describe())   // correct Area() chosen at runtime

let total = shapes |> List.sumBy (fun s -> s.Area())
printfn ""
printfn "Total area of all shapes: %.2f" total