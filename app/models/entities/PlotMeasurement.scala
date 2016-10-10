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
)

object PlotMeasurement {
  type PlotMeasurementID = UUID
  implicit val plotMeasurementFormat: OFormat[PlotMeasurement] = Json.format[PlotMeasurement]
}

