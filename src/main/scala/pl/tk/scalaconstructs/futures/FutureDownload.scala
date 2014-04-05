package pl.tk.scalaconstructs.futures

import scala.io.Source
import java.nio.charset.StandardCharsets
import java.net.{URI, URL}
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object FutureDownload {


  def downloadPage(url: URL) = Future {
    Source.fromURL(url, StandardCharsets.UTF_8.name()).mkString
  }


  def doIt() {

    val numberOfAddreses = 10

    val pageAddresses: List[String] = (for (i <- 1 to numberOfAddreses) yield s"http://ishotgirls.tumblr.com/page/${i}").toList

    //    val futuresOfString: List[Future[String]] = pageAddresses.map {
    //      pageAddress: String => val url = new URL(pageAddress); downloadPage(url)
    //    }
    //
    //    val results: Future[List[String]] = Future.sequence(futuresOfString)
    //
    //    for (r <- results;
    //         pageContent <- r)
    //      println(pageContent)


    val listOfFutures: List[Future[String]] = (pageAddresses.map {
      x: String => downloadPage(new URL(x))
    }).toList


    val result: Future[List[String]] = Future.sequence(listOfFutures)

    result.onSuccess {
      case x: List[String] => x.foreach(println(_))
    }

    Await.result(result, 35 seconds)
  }


}
