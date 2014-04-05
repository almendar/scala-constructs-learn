package pl.tk.scalaconstructs.stacktraits


trait Calculator {
  def increment(x: Int): Int
}

class RealCalculator extends Calculator {
  override def increment(x: Int) = {
    println(s"increment($x)")
    x + 1
  }
}


trait Logging extends Calculator {
  abstract override def increment(x: Int) = {
    println(s"Logging: $x")
    super.increment(x)
  }
}

trait Caching extends Calculator {

  var cache = Map[Int,Int]()

  abstract override def increment(x: Int) =  cache.get(x) match {
    case Some(y:Int) =>
      println(s"Cache hit: $x")
      y
    case None =>
      println(s"Cache miss: $x")
      val z = super.increment(x)
      cache  = cache + (x->z)
      z
  }
}

trait Validating extends Calculator {
  abstract override def increment(x: Int) =
    if(x >= 0) {
      println(s"Validation OK: $x")
      super.increment(x)
    } else
      throw new IllegalArgumentException(x.toString)
}