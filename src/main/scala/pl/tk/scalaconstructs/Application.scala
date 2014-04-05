package pl.tk.scalaconstructs

import pl.tk.scalaconstructs.stacktraits.CalculatorUsage
import pl.tk.scalaconstructs.streamsprimes.{Primes, Fibs}
import pl.tk.scalaconstructs.futures.FutureDownload
import pl.tk.scalaconstructs.classpathscaning.ClassPathUrlFinder
import pl.tk.scalaconstructs.scalapaths.SPath._
import java.nio.file.Path
import pl.tk.scalaconstructs.recursivepowerof.PowerOfCalculator


object Application extends App {
//  FutureDownload.doIt()
//
//  CalculatorUsage.useCalculator()
//  println(Fibs.fibs.takeWhile(_ < 1000).toList)
//  println(Primes.getPrimes.takeWhile(_._1 < 1000).withFilter(_._2).map(_._1).toList)
//
//  val url = ClassPathUrlFinder.findClassPaths().filter(_.toString.endsWith("/"))
//
//
//  ClassPathUrlFinder.findClassesByPackage("pl.tk.typeclassfun").foreach(println(_))
//
//  val a : Path = """c:\"""
//
//  println(a.getFileName)


  println(PowerOfCalculator.power(3,3))

}



