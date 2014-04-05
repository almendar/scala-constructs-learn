package pl.tk.scalaconstructs.typeclasses

import java.net.URL
import java.nio.file.{Paths, Path}

/**
 * Created by tomaszk on 17.02.14.
 */



object ImplicitClasses {

  implicit class UrlWithDownloadMethod(url:URL) {
    def download(where:Path) {
      println(s"Downloading $url to ${where.toAbsolutePath}")
    }
  }


  def main(args: Array[String]) {
    val u = new URL("http://www.fake.com/some_image.jpg")
    u.download(Paths.get("."))
  }

}
