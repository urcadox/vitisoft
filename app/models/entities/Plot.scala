package models.entities

import java.util.UUID
import java.time.OffsetDateTime
import play.api.libs.json._

case class Plot (
  id: Plot.PlotID,
  userId: User.UserID,
  name: String,
  position: JsValue, // {type: "Polygon", coordinates: [[x, y], [...]]}
  createdAt: OffsetDateTime
)

object Plot {
  type PlotID = UUID
}
