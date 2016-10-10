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
import userKinds._

/**
 * A user of the application
 *
 * @param passwordHash  the user's password, hashed with bcrypt
 * @param kind          the user's role, see [[models.entities.userKinds]]
 * @see [[models.entities.userKinds]]
 */
case class User (
  id: User.UserID,
  email: String,
  passwordHash: String,
  name: String,
  kind: UserKind,
  createdAt: OffsetDateTime
)

object User {
  type UserID = UUID
  implicit val userFormat: OFormat[User] = Json.format[User]
}

@ImplementedBy(classOf[UserDAOMongoDB])
trait UserDAO {
  def getAll(): Future[List[User]]
  def getById(id: UUID): Future[Option[User]]
}

class UserDAOMongoDB @Inject() (
  implicit val mongo: ReactiveMongoApi,
  val ec: ExecutionContext
) extends UserDAO {
  val collection = mongo.db.collection[JSONCollection]("users")

  def getAll(): Future[List[User]] = {
    collection.find(Json.obj()).cursor[User]().collect[List]()
  }

  def getById(id: UUID): Future[Option[User]] = {
    collection.find(Json.obj("id" -> id)).cursor[User]().headOption
  }
}
