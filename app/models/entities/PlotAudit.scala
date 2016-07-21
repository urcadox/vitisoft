package models.entities

import java.util.UUID
import java.time.LocalDate

case class PlotAudit (
  id: PlotAudit.PlotAuditID,
  userId: User.UserID,
  date: LocalDate
)

object PlotAudit {
  type PlotAuditID = UUID
}
