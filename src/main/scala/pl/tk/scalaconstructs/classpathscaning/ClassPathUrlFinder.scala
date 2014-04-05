package pl.tk.scalaconstructs.classpathscaning

import java.net.{URLClassLoader, URI, URL}
import java.io.File
import java.nio.file.Path
import java.util.jar.{JarEntry, JarFile}

/**
 * Created with IntelliJ IDEA.
 * User: tomas_000
 * Date: 24.06.13
 * Time: 21:52
 * To change this template use File | Settings | File Templates.
 */
object ClassPathUrlFinder {

  def findClassPaths(): List[URI] = {
    val classPath = System.getProperty("java.class.path")
    classPath.split(File.pathSeparatorChar).toList.map {
      p: String =>
        val f = new File(p);
        if (!f.exists()) throw new RuntimeException(s"${p} does not exist on classpath, even thought it was delivered by system property")
        else f.toURI
    }
  }

  def findClassFiles(lst: List[URI]): List[URI] = {

    def liftListFiles(f: File): Option[Array[File]] = {
      Option(f.listFiles())
    }

    def findClassFilesBring(lst: List[URI], already: List[URI]): List[URI] = {
      lst match {
        case Nil => already
        case x :: Nil =>
          if (x.toString.endsWith(".class")) x :: already
          else {
            val f = new File(x);
            findClassFilesBring(liftListFiles(f).getOrElse(Array()).map(_.toURI).toList, already)
          }
        case x :: xs =>
          if (x.toString.endsWith(".class")) x :: findClassFilesBring(xs, already)
          else if (x.toString.endsWith(".jar")) {
            val jarFile = new JarFile(new File(x))
            val jEntry : JarEntry = jarFile.entries().nextElement()
            //URL u = new URL("jar", "", x + "!/");
            //jarFile.getClass.getResource("/scala/tools/jline/WindowsTerminal.class") tak to zrobić można
            //This has to be solved somehow.
            List()
          }
          else {
            val f = new File(x)
            findClassFiles(liftListFiles(f).getOrElse(Array()).map(_.toURI).toList) ::: findClassFilesBring(xs, already)
          }
      }
    }
    findClassFilesBring(lst, Nil)
  }


  def findClassesByPackage(packageName: String): List[URI] = {
    findClassFiles(findClassPaths()).filter {
      p: URI => p.toString.replace("/", ".").contains(packageName)
    }
  }


}
