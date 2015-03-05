package pl.tk.scalaconstructs

trait FunctorObject[A,F[_]] {
  def map[B <: A](mapper : A => B) : F[B]
}

trait FunctorStandalone[F[_]] {
  def map[A,B](value : F[A])(f : A =>B) : F[B]
}


object FunctorStandalone {
  implicit def listFunctor = new FunctorStandalone[List] {
    def map[A,B](value : List[A])(f:A=>B) = value map (f(_))
  }

  implicit def intFunctor = new FunctorStandalone[Option] {
    def map[A,B](value : Option[A])(f:A=>B) = value map(f(_))
  }

  implicit def eitherFunctor[R] = new FunctorStandalone[({type lambda[P] = Either[R,P]})#lambda] {
    def map[A,B](value : Either[R,A])(f:A=>B) = value match {
      case Right(v) => Right(f(v))
      case Left(v) =>  Left(v)
    }
  }

  implicit class EitherOps[L,R](e : Either[L,R])  extends FunctorObject[R,({type lambda[P] = Either[L,P]})#lambda] {
    def map[B](f : R => B) : Either[L,B] = {
      e match {
        case Right(v) => Right(f(v))
        case Left(l) => Left(l)
      }
    }

    def flatMap[B](f : R => Either[L,B]) = {
      e match {
        case Right(v) => f(v)
        case Left(l) => Left(l)
      }
    }
  }
}


case class ListFunctor[T](lst : List[T]) extends FunctorObject[T,List] {
  def map[B](f : T => B) : List[B] = lst.map(f)
}



object Appendex  extends App {

  import FunctorStandalone._

  println(implicitly[FunctorStandalone[Option]].map(Some(2))(_+2))

  val e1 : Either[Int,String] = Right("22")
  val e2 : Either[Int,String] = Right("22")
  val e3 : Either[Int,String] = Left(22)


  val e = for {
    i <- e1
    j <- e2
    k <- e3
  } yield i +"@"+ j + "@" + k

  println(e)



  //println(e.map(x => x*2))


  // implicit def listFunctor = new Functor[List] {
  // 	def map[A,B](arg : List[A])(mapper : A => B) : List[B] = {
  // 		arg map mapper
  // 	}
  // }

  // type ValOrThrowable[A] = Either[Throwable,A]


  // implicit def EitherFunctor = new Functor[ValOrThrowable] {
  // 	def map[A,B](arg : ValOrThrowable[A])(mapper : A => B) : ValOrThrowable[B] = {
  // 		arg match {
  // 			case Left(t) => Left(t)
  // 			case Right(a) => Right(mapper(a))
  // 		}
  // 	}
  // }



  // implicit def EitherFunctor1[S] = new Functor[Either[S,_]] {
  // 	def map[A,B](arg : Either[S,A])(mapper : A => B) = ???
  // }


  // implicit def EitherFunctor2[S] = new Functor[ ({type lambda[A] = Either[S,A] })#lambda] {
  // 	def map[A,B](arg : Either[S,A])(mapper : A => B) : Either[S,B] = {
  // 		arg match {
  // 			case Left(t) => Left(t)
  // 			case Right(a) => Right(mapper(a))
  // 		}
  // 	}
  // }



  //def transforme[A,B, C[_] : Functor](arg : C[A])(lambda : A=>B ) : C[B] = implicitly[Functor[C]].map(arg)(lambda)


  //println(transforme(List[Int](1,2,3,4,4))((p:Int) => p.toString+"@"))

}

