package pl.tk.scalaconstructs.higerorderfunc



object EmailAddress {
  def unapply(s:String) : Option[EmailAddress] = {
    s.split('@') match {
      case Array(x,y) => Some(new EmailAddress(x,y));
      case _ => None
    }
  }

  implicit def StringToEmailAddress(string : String) : EmailAddress = string match {
    case EmailAddress(emailAddress) => emailAddress
    case _ => null
  }
}

class EmailAddress(val user:String,val domain:String) {
  override def toString = user + "@" + domain;
}


object PredicatesUtil {
  /**
   * Reverses the predicate
   */
  def complement[A](predicate: A => Boolean) : (A => Boolean) = {a =>
    !predicate(a)
  }
  def complementShort[B](predicate: B => Boolean) = !predicate(_:B)

  def any[A](predicates: (A => Boolean)*): A => Boolean =
    a => predicates.exists(pred => pred(a))
  def none[A](predicates: (A => Boolean)*) = complement(any(predicates: _*))
  def every[A](predicates: (A => Boolean)*) = none(predicates.view.map(complement(_)): _*)

}


case class Email(
  subject : String,
  text : String,
  sender: EmailAddress,
  recipient : EmailAddress
)

object Email {
  type EmailFilter = Email => Boolean
  type SizeChecket = Int => Boolean
  val sizeConstraint: SizeChecket => EmailFilter = f => email => f(email.text.size)
  val minimumSize: Int => EmailFilter = n => sizeConstraint(_ >= n)
  val maximumSize: Int => EmailFilter = n => sizeConstraint(_ <= n)

  def newMailForUser(mails:Seq[Email], f:EmailFilter) = mails.filter(f);

  val sentByOneOf: Set[EmailAddress] => EmailFilter =
    senders => email => senders.contains(email.sender)
  val notSentByAnyOf: Set[EmailAddress] => EmailFilter =
        sentByOneOf.andThen(PredicatesUtil.complement(_))


  val addMissingSubject = (email: Email) =>
    if (email.subject.isEmpty) email.copy(subject = "No subject")
    else email
  val checkSpelling = (email: Email) =>
    email.copy(text = email.text.replaceAll("your", "you're"))
  val removeInappropriateLanguage = (email: Email) =>
    email.copy(text = email.text.replaceAll("dynamic typing", "**CENSORED**"))
  val addAdvertismentToFooter = (email: Email) =>
    email.copy(text = email.text + "\nThis mail sent via Super Awesome Free Mail")



}


object Main {
  import pl.tk.scalaconstructs.higerorderfunc.Email.EmailFilter

  def main(args: Array[String]) {
    val email = Email("Some", "Some text", "joe@email.com","johny@doom.com")

    val emailAddress : Option[EmailAddress] = Option("joegmail.com")

    emailAddress match {
     case Some(x) => println("Hello:" + x)
     case None => println("Null address")
    }


    val filter: EmailFilter = PredicatesUtil.every(
      Email.notSentByAnyOf(Set("johndoe@example.com")),
      Email.minimumSize(100),
      Email.maximumSize(10000)
    )


    import Email._
    val pipeline: (Email) => Email = Function.chain(Seq(
      addMissingSubject,
      checkSpelling,
      removeInappropriateLanguage,
      addAdvertismentToFooter))

    def chain[a](fs: Seq[a => a]): a => a = { x => (x /: fs) ((x, f) => f(x)) }

    def chain1[a](fs: Seq[a => a]) : a => a = {x => fs.foldLeft(x)((x,f) => f(x)) }

    val c : PartialFunction[Int,String] = {
      case 0 => "Zero"
      case 1 => "One"
      case 2 => "Zero"
    }

    val cLifted: (Int) => Option[String] = c.lift

    val i: (Seq[Email]) => Seq[Email] = newMailForUser(_:Seq[Email], Email.minimumSize(2))




  }
}
