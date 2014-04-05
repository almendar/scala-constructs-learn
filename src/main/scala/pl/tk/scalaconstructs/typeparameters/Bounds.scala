package pl.tk.scalaconstructs.typeparameters

import scala.collection.SortedSet


/**
*
* Upper bounds may be a little to much as it can infere with
 *
*
*/
class Pair[-T](r:T,t:T) {
  /**
   * Here the compareTo is applicable because we have a higherbound o Comparable[T]
   * @return
   */
//  def sizeRelationship(implicit ev: T <:< Comparable[T]) = r.compareTo(t)

  /**
   * This gives upper bounds
   */
//  def replaceFirst[ R >: T](first:R): Pair[R] = new Pair[R](first,t)
}

sealed class Person(val importance:Int) extends Comparable[Person] {
  override def compareTo(o: Person): Int = ???
}

class Student extends Person(10)
class Teacher extends Person(50)




object Bounds {

  implicit def student2Comparable(s:Student) : Comparable[Student] = new Comparable[Student] {
    override def compareTo(o: Student): Int = s.compareTo(o)
  }

//  implicit val ev : <:<[Student,Comparable[Student]] = new <:<[Student,Comparable[Student]]{
//    override def apply(v1: Student): Comparable[Student] = new Comparable[Student] {
//      override def compareTo(o: Student): Int = v1.importance - o.importance
//    }
//  }

  def main(args: Array[String]) {
    SortedSet
    List(1,2,3).sorted
    val mixed: Pair[Person] = new Pair(new Student,new Teacher)
//    mixed.sizeRelationship
    val notMixed = new Pair[Student](new Student, new Student)
//    notMixed.sizeRelationship
//    val mixedAgain: Pair[Person] = mixed.replaceFirst(new Teacher)

//    val sortedPair = new SortedPair[Person](new Student,new Teacher)


  }
}
