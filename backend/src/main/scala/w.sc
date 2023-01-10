class Person(val name: String, val age: Int)

object Person {
  def apply(p1: Person, p2: Person): Person = {
    val name = s"Son of ${p1.name} and ${p2.name}"
    val age = 0
    new Person(name, age)
  }

  def apply(name: String): Person = new Person(name, 0)
  def apply(age: Int): Person = new Person("unknown", age)
}

val p1 = new Person("Tom", 2)
val p2 = new Person("Anna", 3)
Person.apply("Ma")
Person.apply(10)

//////
trait Animal {
  def sleep = "ZzZz"
  def eat(food: String): String
  def move(x: Int, y: Int): String
}

trait Nameable {
  def name: String
}

class Dog(val name: String) extends Animal with Nameable {
  override def eat(food: String): String = s"dog eats $food"
  override def move(x: Int, y: Int): String = s"dog moves to ($x, $y)"
}

def feedTreat(animal: Animal) =
  animal.eat("treat")

def welcome(nameable: Nameable) =
  println(s"Hi, ${nameable.name}")

//////

sealed trait Suit
object Clubs extends Suit
object Diamonds extends Suit
object Hearts extends Suit
object Spades extends Suit

sealed trait Currency
object USD extends Currency
object EUR extends Currency
object CAD extends Currency

def exchangeRateUSD(currency: Currency): Double =
  currency match {
    case USD => 1
    case EUR => 0.98
    case CAD => 1.2
  }
/////// anon function

object Calc {
  def sum(a: Int, b: Int): Int = a + b
  val sum: (Int, Int) => Int = (a: Int, b: Int) => a + b
  val sumUnderscored: (Int, Int) => Int = { _ + _ }
}
/// partial function

val sqrt: PartialFunction[Int, Double] =
  { case x if x >= 0 => Math.sqrt(x) }
val zero: PartialFunction[Int, Double] =
  { case _ => 0 }
val value: PartialFunction[Int, Double] =
  { case x => x }
sqrt.orElse(value).orElse(zero)(4)
zero.orElse(sqrt)

def sqrtOfZero(n: Int): Double = n match {
  case x if x >= 0 => Math.sqrt(x)
  case _ => 0
}

def sqrtOrValue(n: Int): Double = n match {
  case x if x >= 0 => Math.sqrt(x)
  case x => x
}

val transform: PartialFunction[String, String] = {
  case s if s.startsWith("a") => s.reverse
  case s if s.startsWith("x") => s.toUpperCase
}

/// function composition
val f: String => Int = _.length
val g: Int => Boolean = _ > 2
val gof: String => Boolean = { s => g(f(s)) }
val gof2: String => Boolean = f.andThen(g)


/// hof

def stats(s: String, p: Char => Boolean): Int =
  s.count(p)

val size: String => Int = stats(_, _ => true)
val countLetters: String => Int = stats(_, _.isLetter)
val countDigits: String => Int = stats(_, _.isDigit)
val countX: String => Int = stats(_, _ == 'x')
