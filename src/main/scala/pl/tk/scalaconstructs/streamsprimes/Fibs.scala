package pl.tk.scalaconstructs.streamsprimes


object Fibs {
  def fibs: Stream[BigInt] = BigInt(0) #:: BigInt(1) #:: fibs.zip(fibs.tail).map { n => n._1 + n._2 }
}
