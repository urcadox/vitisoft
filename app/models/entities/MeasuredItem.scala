package models.entities

import play.api.libs.json._
import play.api.data.format.Formatter
import models.enums
import scalaz.Enum

object measuredItems {
  sealed trait MeasuredItem
  case object Temperature    extends MeasuredItem
  case object Weather        extends MeasuredItem
  case object Humidity       extends MeasuredItem
  case object Pesticide      extends MeasuredItem
  case object Picture        extends MeasuredItem

  val vs: Set[MeasuredItem] = Set(
    Temperature,
    Weather,
    Humidity,
    Pesticide,
    Picture
  )

  implicit val measuredItemEnum: Enum[MeasuredItem] = enums.enumFromList(vs.toList)
  implicit val measuredItemReads: Reads[MeasuredItem] = enums.jsonReads(x => vs.find(v => v.toString == x))
  implicit val measuredItemWrites: Writes[MeasuredItem] = enums.jsonWrites(_.toString)

  implicit val measuredItemFormat: Formatter[MeasuredItem] = enums.enumFormat[MeasuredItem](s =>
    vs.find(v => v.toString == s)
  )
}

