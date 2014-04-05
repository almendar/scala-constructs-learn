package pl.tk.scalaconstructs.liftfunction

/**
 * Created with IntelliJ IDEA.
 * User: tomas_000
 * Date: 08.07.13
 * Time: 18:04
 * To change this template use File | Settings | File Templates.
 */
object Lift {

  def _1[A, R](f: Function1[A, R]): Function1[Option[A], Option[R]] = {
    (a: Option[A]) => {
      for (aa <- a) yield f(aa)
    }
  }

  def _2[A, B, R](f: Function2[A, B, R]): Function2[Option[A], Option[B], Option[R]] = {
    (a: Option[A], b: Option[B]) => {
      for (aa <- a; bb <- b) yield f(aa, bb)
    }
  }

}
