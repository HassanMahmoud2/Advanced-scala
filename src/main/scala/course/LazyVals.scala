package course

object LazyVals extends App{

  // Eagerly evaluated variable
  val eagerValue: Int = {
    println("Eagerly initializing eagerValue")
    42
  }

  // Lazy-initialized variable
  lazy val lazyValue: Int = {
    println("Lazily initializing lazyValue")
    42
  }

  println("Before accessing values")

  // Accessing the eagerly evaluated variable
  println("Eager value: " + eagerValue)

  println("After accessing eagerValue, before accessing lazyValue")

  // Accessing the lazily initialized variable
  println("Lazy value: " + lazyValue)

  // Accessing the lazily initialized variable again
  println("Lazy value again: " + lazyValue)
}