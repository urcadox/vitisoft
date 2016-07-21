package models.entities

import java.util.UUID
import java.time.OffsetDateTime
import userKinds._

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
}
