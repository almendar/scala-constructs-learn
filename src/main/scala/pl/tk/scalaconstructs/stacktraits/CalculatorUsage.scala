package pl.tk.scalaconstructs.stacktraits

/**
 * Created with IntelliJ IDEA.
 * User: tomas_000
 * Date: 19.06.13
 * Time: 12:18
 * To change this template use File | Settings | File Templates.
 */
object CalculatorUsage {

  def useCalculator() {
    val c = new RealCalculator with Logging with Caching with Validating
    val x = c.increment(17)
    println(s"$x")
    val z = c.increment(17)
    println(s"$z")
  }

}
