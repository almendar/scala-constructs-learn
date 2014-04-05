package pl.tk.scalaconstructs.scalapaths

import java.nio.file.{Path, Paths}

/**
 * Created with IntelliJ IDEA.
 * User: tomas_000
 * Date: 25.06.13
 * Time: 08:41
 * To change this template use File | Settings | File Templates.
 */
object SPath {
  implicit def String2Path(value : String) : Path =
    Paths.get(value)
}
