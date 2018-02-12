package repositories

import java.util.UUID

import com.google.inject.ImplementedBy
import models.{Email, User}
import org.mindrot.jbcrypt.BCrypt

@ImplementedBy(classOf[AnormUserRepository])
trait UserRepository {
  def create(email: Email, password: String): Unit = {
    val salt: String = BCrypt.gensalt()
    User(UUID.randomUUID(), email, BCrypt.hashpw(password, salt), salt)
    // TODO: save user
  }

  def get(id: UUID): Option[User]
}
