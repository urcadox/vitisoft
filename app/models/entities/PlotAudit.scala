package models.entities

import java.util.UUID
import java.time.LocalDate
import play.api.libs.json._

case class PlotAudit (
  id: PlotAudit.PlotAuditID,
  userId: User.UserID,
  date: LocalDate,
  measurements: List[PlotMeasurement]
)

object PlotAudit {
  type PlotAuditID = UUID
  implicit val plotAuditFormat: OFormat[PlotAudit] = Json.format[PlotAudit]
}
