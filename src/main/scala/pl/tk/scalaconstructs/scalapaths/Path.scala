package pl.tk.scalaconstructs.scalapaths

import java.nio.file.{Path, Paths}


object SPath {
  implicit def String2Path(value : String) : Path =
    Paths.get(value)
}
