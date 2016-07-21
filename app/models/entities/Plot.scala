package models.entities

import java.util.UUID
import java.time.OffsetDateTime
import org.postgis._

case class Plot (
  id: Plot.PlotID,
  userId: User.UserID,
  name: String,
  position: PGgeometry,
  createdAt: OffsetDateTime
)

object Plot {
  type PlotID = UUID
}
