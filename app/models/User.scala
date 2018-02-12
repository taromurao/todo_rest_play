package models

import java.util.UUID

case class User(id: UUID, email: Email, password: String, salt: String)
