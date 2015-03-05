package pl.tk.scalaconstructs

import scala.concurrent.Future
import scala.util.{Try,Success,Failure}
import scala.concurrent.ExecutionContext.Implicits.global

//Future[Option[A]]
//Future[A]


case class User(name:String,roles:List[String])

object DB {

  def getUser(userid:Long) : Future[Option[User]] = {
    Future{
      println(s"Getting user with id $userid")
      if(userid < 10)
        Some(User(s"$userid", List.empty))
      else None
    }

  }

  def getUserRoles(userid : Long) : Future[List[String]] = {
    Future {
      println(s"Getting roles for user $userid")
      List("Role1","Role2")
    }
  }

  def getUserWithRoles(userid : Long) : Future[Option[User]] = {
    import FutureOption._
    for {
      user  <- DB.getUser(userid) : FutureOption[User]
      roles1 <- DB.getUserRoles(userid)
    } yield user.copy(roles = roles1)
  }

}


case class FutureOption[R](run : Future[Option[R]]) {
  def map[B](f : R => B) : FutureOption[B] =  FutureOption(run map { _ map f})
  def flatMap[B](f : R => FutureOption[B]) : FutureOption[B] =  FutureOption {
    run flatMap {
      case Some(r)=>
        f(r).run flatMap {
          case p => Future.successful(p)
        }
      //
      case None => Future.failed(null)
    }
  }
}


object FutureOption  {


  implicit def withFuture[R](run : Future[R]): FutureOption[R] =  FutureOption(run.map(Option(_)))
  implicit def withOption[R](run : Option[R]): FutureOption[R] =  FutureOption(Future.successful(run))

  implicit def toFutureOptionT[R](run : Future[Option[R]]) : FutureOption[R] =  FutureOption(run)
  implicit def fromFutureOptionT[R](futOpt : FutureOption[R]) : Future[Option[R]] = futOpt.run
}

class Tryout {

  DB.getUserWithRoles(22)
  DB.getUserWithRoles(9)

}
