package models.entities

import java.util.UUID
import java.time.OffsetDateTime
import play.api.libs.json._
import measuredItems._

/**
 * A measurement made on a plot. The value is specific to the kind of item.
 */
case class PlotMeasurement (
  id: PlotMeasurement.PlotMeasurementID,
  plotAuditId: PlotAudit.PlotAuditID,
  item: MeasuredItem,
  value: JsValue,
  timestamp: OffsetDateTime
) {
  def display() = {
    val v = value.as[String]
    item match {
      case Temperature => s"$v °C"
      case Humidity => s"$v %"
      case Pesticide => s"$v %"
      case _ => v
    }
  }
}

object PlotMeasurement {
  type PlotMeasurementID = UUID
  implicit val plotMeasurementFormat: OFormat[PlotMeasurement] = Json.format[PlotMeasurement]
}

