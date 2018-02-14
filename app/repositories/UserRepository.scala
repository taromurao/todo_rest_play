package repositories

import java.util.UUID

import com.google.inject.ImplementedBy
import models.{Email, User}
import org.mindrot.jbcrypt.BCrypt

@ImplementedBy(classOf[AnormUserRepository])
trait UserRepository {
  def create(id: UUID, email: Email, password: String): Unit

  def create(email: Email, password: String): Unit = {
    val id: UUID = UUID.randomUUID()
    create(id, email, password)
  }

  def get(id: UUID): Option[User]
}
