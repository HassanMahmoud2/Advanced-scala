package course.typeSystem

object variances extends App {

  /********** Invariants ***************/
  class Box[T](var content: T) {
    println(s"box of $content")
  }

  abstract class Animal:
    def name: String

  case class Cat(name: String) extends Animal
  case class Dog(name: String) extends Animal
  var cat: Animal = Cat("Felix")
  val myCatBox: Box[Cat] = Box[Cat](Cat("Felix"))

  /**
   *   the below line doesn't compile because myAnimalBox is of type animal while myCatBox is of type cat
   *   val myAnimalBox: Box[Animal] = myCatBox
   *   we have to conclude that Box[Cat] and Box[Animal] can’t have a subtyping relationship, even though Cat and Animal do.
   */

  /********** Covariances ************** */

   /**
    * covariants solves the above problem, since that gives us the following relationship:
    * given some class Cov[+T], then if A is a subtype of B, Cov[A] is a subtype of Cov[B].
    * This allows us to make very useful and intuitive subtyping relationships using generics.
    */

   def printAnimalNames(animals: List[Animal]): Unit =
     animals.foreach {
       animal => println(animal.name)
     }

  val cats: List[Cat] = List(Cat("Whiskers"), Cat("Tom"))
  val dogs: List[Dog] = List(Dog("Fido"), Dog("Rex"))

  // prints: Whiskers, Tom
  printAnimalNames(cats)

  // prints: Fido, Rex
  printAnimalNames(dogs)

  /** ******** Contravariance ************** */

  /**
   * that gives us the reverse relationship: given some class Contra[-T],
   * then if A is a subtype of B, Contra[B] is a subtype of Contra[A].
   */

  abstract class Serializer[-A]:
    def serialize(a: A): String

  val animalSerializer: Serializer[Animal] = new Serializer[Animal]():
    def serialize(animal: Animal): String = s"""{ "name": "${animal.name}" }"""

  val catSerializer: Serializer[Cat] = animalSerializer
  catSerializer.serialize(Cat("Felix"))
}
