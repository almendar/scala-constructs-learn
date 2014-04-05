package pl.tk.typeclassfun

import java.util.Date


trait SumMachine[T] {
  def +:(data : List[T]) : T
}


object ConcreteSumMachines {
  implicit object DatePrinting extends SumMachine[String] {
    override def +:(data : List[String]) : String = {
      data.foldRight("")(_+_)
    }
  }

  implicit object IntegerSumMachine extends SumMachine[Int] {
    def +:(data: List[Int]): Int = {
      data.foldRight(0)(_+_)
    }
  }

  implicit object CharSumMachine extends SumMachine[Char] {
    def +:(data: List[Char]): Char = {
      data.foldRight(0)(_+_).toChar
    }
  }

  implicit object DateSumMachine extends SumMachine[java.util.Date] {
    def +:(data: List[Date]): Date = {
      val sum = data.map(_.getTime).foldRight(0L)(_+_)
      new java.util.Date(sum)
    }
  }
}

class SumMachineUsage {

  import ConcreteSumMachines._

  def sumMyData[A](xs: List[A])(implicit m: SumMachine[A]) = {
    xs +: m
  }

  def exampleUsage() {
    println(sumMyData(List(1, 2, 3, 4)))
    println(sumMyData(List(new java.util.Date, new java.util.Date, new java.util.Date)))
  }

}



