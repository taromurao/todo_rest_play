package repositories

import java.util.UUID

import models.{Email, User}

class AnormUserRepository extends UserRepository {
  override def create(email: Email, password: String): Unit = Unit

  override def get(id: UUID): Option[User] = ???
}
