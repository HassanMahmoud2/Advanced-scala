package course

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try

object Monads extends App{
  /* Option Monad :
    Represents computations that might succeed or fail.
    Helps avoid null references
   */
  val maybeValue: Option[Int] = Some(42)

  val optionResult: Option[String] = maybeValue.flatMap { value =>
    if (value > 0) Some(s"Positive value: $value")
    else None
  }
  // The result is Some("Positive value: 42")
  println(optionResult)

  /*
    Future Monad :
    Represents asynchronous computations.
    Allows composing and chaining asynchronous operations.
  */
  val futureResult: Future[Int] = Future {
    // Simulating asynchronous computation
    Thread.sleep(10)
    42
  }
  futureResult.map { value =>
    // The result is printed after the Future completes
    println(s"Async result: $value")
  }

  /*
    Try Monad :
    Represents computations that may result in a value or an exception.
    Useful for error handling.
  */
  val result: Try[Int] = Try {
    "123".toInt
  }
  result match {
    case scala.util.Success(value) => println(s"Success: $value")
    case scala.util.Failure(exception) => println(s"Error: ${exception.getMessage}")
  }

  /*
    Either Monad :
    Represents computations that may result in one of two possible values.
    Useful for handling multiple outcomes.
  */
  val eitherResult: Either[String, Int] = if (scala.util.Random.nextBoolean()) Left("Error") else Right(42)
  eitherResult.fold(
    error => println(s"Error: $error"),
    value => println(s"Success: $value")
  )

 /*
  List Monad :
  Represents computations that result in multiple values.
  Useful for working with collections of values.
 */
  val numbers: List[Int] = List(1, 2, 3, 4)
  val squaredNumbers: List[Int] = numbers.flatMap { n =>
    List(n, n * n)
  }
  // The result is List(1, 1, 2, 4, 3, 9, 4, 16)
  println(squaredNumbers)
}
