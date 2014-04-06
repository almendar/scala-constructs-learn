package pl.tk.scalaconstructs.destructors

import scala.util.{Success, Failure, Try}
import java.lang.Character
import java.net.URL

object Fraction {
  def unapply(s: String): Option[Fraction] = {
    s.split("/") match {
      case Array(a, b) if (a.forall(Character.isDigit) && b.forall(Character.isDigit)) => Some(Fraction(a.toInt, b.toInt))
      case _ => None
    }

    //    def unapplySeq(s:String) : Option[Seq[Fraction]] = {
    //      val a = s.split("/").map(_.toInt)
    //      Seq(Fraction(a(0),(a(1))), Fraction( a(2), a(3) ) )
    //    }
  }
}

object GivenNames {
  def unapplySeq(name: String): Option[Seq[String]] = {
    val names = name.trim.split(" ")
    if (names.forall(_.isEmpty)) None else Some(names)
  }
}

/**
 * This will match only if we have at leas two word in a sequence.
 */
object GiveNameAndSurname {
  def unapplySeq(name: String): Option[(String, String, Seq[String])] = {
    val names = name.trim.split(" ")
    if (names.size < 2) None
    else Some((names.last, names.head, names.drop(1).dropRight(1)))
  }
}

object Email {
  def apply(user:String, domain:String) = user+"@"+domain

  def unapply(address:String) : Option[(String,String)] = {
    address.split("@") match {
      case Array(user,domain) => Some((user,domain))
      case _ => None
    }
  }


}

object IsPolishDomain {
  def unapply(str:String) = str.contains(".pl")
}

object Domain {

// The injection method (optional)
  def apply(parts: String*): String = parts.reverse.mkString(".")

// The extraction method (mandatory)
  def unapplySeq(whole: String): Option[Seq[String]] = Some(whole.split("\\.").reverse)
}



object Foo {
  def main(args:Array[String]) : Unit = {

    val properEmail = "tmkk@o2.pl"


    properEmail match {
      case user Email domain => println("Yes it is")
      case _ => println("No it's not")
    }
    val Email(user,domain @ IsPolishDomain()) = "tmkk@o2.pl"
    println(user,domain)
    val emails = List(properEmail,"zazs@sd.pl","notanemail","amercian@america.com") filter {case Email(_,_) => true; case _ => false} foreach {println}

  val Domain(country, rest @ _*) = "pl.com.wp.finanse" 
  println(rest.last)
  }


}



case class Fraction(a: Int, b: Int)

object URLExtractor {
  def unapply(str: String): Boolean = {
    Try {
      new URL(str)
    } match {
      case Success(v) => true
      case Failure(t) => false
    }
  }
}


object DoublePrecisionNumberPartsExtractor {

  /**
   * We use AnyVal as this will give opportunity to extract also from Integers, not only Floats and Doubles.
   */
  def unapply(d: AnyVal): Option[(Int, Int)] = {
    d.toString.split('.').toList match {
      case a :: b :: Nil => {
        val parseA = Try {
          a.toInt
        }
        val parseB = Try {
          b.toInt
        }
        val unpacked =
          for {aVal <- parseA
               bVal <- parseB}
          yield Some((aVal, bVal))
        unpacked.getOrElse(None)
      }
      case a :: Nil =>
        Try {
          a.toInt
        } match {
          case Failure(exception) => None
          case Success(value) => Some((value, 0))
        }
      case _ => None
    }
  }


  def main(args: Array[String]) {


    /**
     * Extractor used in assigment
     */
    val DoublePrecisionNumberPartsExtractor(a, b) = -2.4;

    val Fraction(l) = "2/3"
    /**
     * Extractor used in pattern matching on the left side
     */
    "2/3" match {
      case Fraction(l) => println(l)
      case _ => println("Damn it")
    }

    /**
     * Extractor used in the for-comprehension
     * Failed ones will be silently skiped
     */
    (for (
      Fraction(f) <- List("8/3", "2/4", "Will skip this silently")
    ) yield f).foreach(println(_))
  }




  val res = "http://www.wp.pl" match {
    case p @ URLExtractor => true
    case _ => false
  }
  println(s"Is URL?: $res")



  var GivenNames(nn,mm) = "Tomek Kogut"
  /**
   * If not given _* Match Error could arise
   */
  var GivenNames(pp,_*) = "This need to look this way"
  println(nn)

  var GiveNameAndSurname(name,surname, _*) = "Piotr Piotrecki"
  println(s"$name $surname")


  /**
   * Annonymouse function with patter matching.
   * Warning if MatchError occures this will fail!!!
   */
  List("23/45","23/456") map {
    case Fraction(f) => f
  } foreach {
    println
  }

  val pf = new PartialFunction[String,Fraction] {

    override def isDefinedAt(x: String): Boolean = x match {
      case Fraction(l) => true
      case _ => false
    }

    override def apply(v1: String): Fraction =
      v1 match {
        case Fraction(l) => l
      }
  }


  List("9/8","9/11") collect pf  foreach println

}