package models

import java.util.UUID

case class User(id: UUID, email: String, password: String, salt: String)
