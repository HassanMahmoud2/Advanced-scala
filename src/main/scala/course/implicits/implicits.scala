package course.implicits

object implicits extends App{
  class Person(name: String) {
    def greeting = s"Hello, my name is $name"
  }
  case class Person2(name: String, age: Int) {
    def greeting = s"Hello, my name is $name"
  }
  implicit def fromStringToPerson(name: String): Person = new Person(name)
  println("Hassan".greeting)

  /***************/

  val persons = List (
    Person2("Hassan", 25),
    Person2("mohamed", 50),
    Person2("Ahmed", 30)
  )
  def increment(x: Int)(implicit y: Int) = x + y
  implicit val amount: Int = 10
  println(increment(2))

  /***************/

  implicit val reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  println(List(1,4,5,3,2).sorted)

  /***************/

  object ageImplicit {
    implicit val ageOrdering: Ordering[Person2] = Ordering.fromLessThan((a, b) => a.age < b.age)
  }
  object nameImplicit {
    implicit val nameOrdering: Ordering[Person2] = Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
  }
  import nameImplicit._
  println(persons.sorted)

  /***************/
  trait HTMLSerializer[T] {
    def serialize(value: T): String
  }
  object HTMLSerializer {
    def serialize[T](value: T)(implicit serializer: HTMLSerializer[T]): String = {
      serializer.serialize(value)
    }
  }
  implicit object IntSerializer extends HTMLSerializer[Int] {
    override def serialize(value: Int): String = s"hello i'm int serlializer with $value"
  }

  implicit object UserSerializer extends HTMLSerializer[Person2] {
    override def serialize(person: Person2): String = s"hello i'm user serializer i have user named ${person.name} and is ${person.age} years old"
  }
  println(HTMLSerializer.serialize(35))
  println(HTMLSerializer.serialize(new Person2("Hassan", 25)))
}
