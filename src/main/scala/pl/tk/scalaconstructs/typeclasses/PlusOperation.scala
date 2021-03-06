package pl.tk.scalaconstructs.typeclasses

trait PlusOperation[A] {
  def plus(a1:A, a2:A) : A

}


object PlusOperation {
  def plus[A:PlusOperation](a1:A,a2:A) : A = implicitly[PlusOperation[A]].plus(a1,a2)

  implicit object PlusOperations extends PlusOperation[String] {
    override def plus(a1: String, a2: String): String = a1 + " " + a2
  }

  def tryeout() {
    val s1 : String = "Firts string"
    val s2 : String = "Second String"
    val s3 = plus(s1,s2)
  }

}


