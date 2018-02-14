package repositories

import java.sql.Connection
import java.util.UUID
import javax.inject.Inject

import anorm._
import play.api.db._
import models.{Email, User}
import org.mindrot.jbcrypt.BCrypt
import play.api.Logger

import scala.util.{Success, Try}

class AnormUserRepository @Inject()(db: Database) extends UserRepository {
  override def create(id: UUID, email: Email, password: String): Unit = {
    val user: User = newUser(id, email, password)
    val query: SqlQuery = SQL(s"""
      INSERT INTO users (id, email, password, salt)
      VALUES ('${user.id}', '${user.email}', '${user.password}', '${user.salt}')
        """)

    db.withConnection { implicit conn: Connection => query.execute() }
  }

  override def get(id: UUID): Option[User] = {
    val query: SqlQuery = SQL(s"SELECT * FROM users WHERE id='$id' LIMIT 1")

    db.withConnection[Option[User]] { implicit conn: Connection =>
      Try(query.as(userParser.single)) match {
        case Success(user) => Some(user)
        case _ => None
      }
    }
  }

  private val userParser: RowParser[User] = Macro.namedParser[User]

  private def newUser(id: UUID, email: Email, password: String): User = {
    val salt: String = BCrypt.gensalt()
    val passwordHash: String = BCrypt.hashpw(password, BCrypt.gensalt())
    User(id, email, passwordHash, salt)
  }
}
