package pl.tk.scalaconstructs.traits

trait TypeOfDay
trait WorkingDay extends TypeOfDay
trait LeisureDay extends TypeOfDay

sealed abstract class DayOfWeek {
  self: TypeOfDay =>
}


case object Monday extends DayOfWeek with WorkingDay
case object Sunday extends DayOfWeek with LeisureDay


class Test extends {

  def printLeisureDay(day : DayOfWeek with LeisureDay) = println(day)
  def printWorkingDay(day : DayOfWeek with WorkingDay) = println(day)
  def printyAnyDay(day : DayOfWeek) = println(day)

  printWorkingDay(Monday)
  printLeisureDay(Sunday)

  printyAnyDay(Sunday)
  printyAnyDay(Monday)
  //printWorkingDay(Sunday)
  //printLeisureDay(Monday)

}