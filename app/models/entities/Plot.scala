package models.entities

import javax.inject._
import com.google.inject.ImplementedBy
import java.util.UUID
import java.time.OffsetDateTime
import play.api.libs.json._
import play.modules.reactivemongo._
import reactivemongo.api._
import reactivemongo.play.json._
import reactivemongo.play.json.collection._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext

/**
 * A plot of land (french: Parcelle)
 */
case class Plot (
  id: Plot.PlotID,
  userId: User.UserID,
  name: String,
  position: JsValue, // {type: "Polygon", coordinates: [[x, y], [...]]}
  pictureUrl: Option[String],
  createdAt: OffsetDateTime,
  audits: List[PlotAudit]
) {
  def sortedAudits = audits.sortBy(_.date.toString)
}

object Plot {
  type PlotID = UUID
  implicit val plotFormat: OFormat[Plot] = Json.format[Plot]
}

@ImplementedBy(classOf[PlotDAOMongoDB])
trait PlotDAO {
  def getAll(): Future[List[Plot]]
  def getById(id: UUID): Future[Option[Plot]]
  def update(id: UUID, plot: PlotInput): Future[Option[Plot]]
}

class PlotDAOMongoDB @Inject() (
  implicit val mongo: ReactiveMongoApi,
  val ec: ExecutionContext
) extends PlotDAO {
  val collection = mongo.db.collection[JSONCollection]("plots")

  def getAll(): Future[List[Plot]] = {
    collection.find(Json.obj()).cursor[Plot]().collect[List]()
  }

  def getById(id: UUID): Future[Option[Plot]] = {
    collection.find(Json.obj("id" -> id)).cursor[Plot]().headOption
  }

  def update(id: UUID, plot: PlotInput): Future[Option[Plot]] = {
    val findQuery = Json.obj("id" -> id)
    val updateQuery = Json.obj("$set" -> Json.toJson(plot))
    collection.update(findQuery, updateQuery).flatMap(result => this.getById(id))
  }
}

/**
 * User input for a plot
 */
case class PlotInput (
  userId: User.UserID,
  name: String,
  position: JsValue,
  pictureUrl: Option[String],
  audits: List[PlotAudit]
)
object PlotInput {
  implicit val plotInputFormat: Format[PlotInput] = Json.format[PlotInput]
}
