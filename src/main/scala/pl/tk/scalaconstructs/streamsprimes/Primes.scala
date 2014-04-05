package pl.tk.scalaconstructs.streamsprimes

object Primes  {
  def isPrime(number:Integer) : Boolean = {
    if(number==0) false
    else if(number==1) true
    else (2 to Math.sqrt(number.toDouble).toInt).forall(number % _ != 0)
  }

  def getPrimes : Stream[(Int,Boolean)] = {
    Stream.from(0).map(x=> (x,isPrime(x)))
  }

}



