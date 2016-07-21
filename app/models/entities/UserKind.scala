package models.entities

import play.api.libs.json._
import play.api.data.format.Formatter
import models.enums
import scalaz.Enum

object userKinds {
  sealed trait UserKind
  case object PlotOwner      extends UserKind
  case object Auditor        extends UserKind

  val vs: Set[UserKind] = Set(
    PlotOwner,
    Auditor
  )

  implicit val userKindEnum: Enum[UserKind] = enums.enumFromList(vs.toList)
  implicit val userKindReads: Reads[UserKind] = enums.jsonReads(x => vs.find(v => v.toString == x))
  implicit val userKindWrites: Writes[UserKind] = enums.jsonWrites(_.toString)

  implicit val userKindFormat: Formatter[UserKind] = enums.enumFormat[UserKind](s =>
    vs.find(v => v.toString == s)
  )
}

