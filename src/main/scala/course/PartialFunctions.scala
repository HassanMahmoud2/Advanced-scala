package course

object PartialFunctions extends App {

  //partial function
  val squareRootImplicit: PartialFunction[Double, Double] = {
    case x if x >= 0 => Math.sqrt(x)
  }
  val output = squareRootImplicit(16)
  println(output)

  //chaining it with orElse
  val negativeOrZeroToPositive: PartialFunction[Int, Int] = {
    case x if x <= 0 => Math.abs(x)
  }
  val positiveToNegative: PartialFunction[Int, Int] = {
    case x if x > 0 => -1 * x
  }

  val swapSign: PartialFunction[Int, Int] = {
    positiveToNegative orElse negativeOrZeroToPositive
  }
  println(swapSign(4))

  //chaining it with andThen
  val printIfPositive: PartialFunction[Int, Unit] = {
    case x if x > 0 => println(s"$x is positive!")
  }
  println((swapSign andThen printIfPositive)(-1))
  
}