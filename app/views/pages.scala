package views

sealed abstract trait Section
object Section {
  case object Home                  extends Section
  case object Plots                 extends Section
  case object Audits                extends Section
}
