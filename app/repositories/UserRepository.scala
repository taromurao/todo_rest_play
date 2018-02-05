package repositories

import java.util.UUID

import models.{Email, User}
import org.mindrot.jbcrypt.BCrypt

trait UserRepository {
  def create(email: Email, password: String): Unit = {
    val salt: String = BCrypt.gensalt()
    User(UUID.randomUUID(), email, BCrypt.hashpw(password, salt), salt)
    // TODO: save user
  }
}
