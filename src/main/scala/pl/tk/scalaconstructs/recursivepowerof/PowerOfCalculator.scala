package pl.tk.scalaconstructs.recursivepowerof

/**
 * Created with IntelliJ IDEA.
 * User: tomas_000
 * Date: 03.07.13
 * Time: 10:49
 * To change this template use File | Settings | File Templates.
 */
object PowerOfCalculator {

  private def isOdd(x:Int) : Boolean = x%2 == 0
  private def isEven(x:Int) : Boolean = !isOdd(x)
  private def isZero(x:Int) : Boolean = !(isPositive(x) && isNegative(x))
  private def isPositive(x:Int) : Boolean = x > 0
  private def isNegative(x:Int) : Boolean = x != 0 && !isPositive(x)


  def power(x:Int,n:Int) : Double = {
    if(n==0) 1.0
    else if(isNegative(n)) (1 / power(x,-n))
    else if(isOdd(n)) x * power(x,n-1)
    else if(isEven(n)) power(x,n/2) * power(x,n/2)
    else throw new Exception("Somethin bad has happend");
  }

}
