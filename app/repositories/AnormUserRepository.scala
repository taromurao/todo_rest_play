package repositories

import models.Email

class AnormUserRepository extends UserRepository {
  override def create(email: Email, password: String): Unit = Unit
}
