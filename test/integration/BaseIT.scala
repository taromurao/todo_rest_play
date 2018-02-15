package integration

import java.sql.Connection
import java.util.UUID

import models.Email
import org.scalatest.{BeforeAndAfter, BeforeAndAfterEachTestData}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.libs.ws._
import repositories.UserRepository
import anorm._
import play.api.db.Database

abstract class BaseIT extends PlaySpec with GuiceOneServerPerSuite with BeforeAndAfter{

  val A_PASSWORD = "1234567890"
  val AN_EMAIL: Email = "john@example.com"
  val A_USER_ID: UUID = UUID.fromString("d824a403-d159-4dcd-966c-0096274cd612")

  protected val userRepository: UserRepository = app.injector.instanceOf[UserRepository]
  protected val wsClient: WSClient = app.injector.instanceOf[WSClient]
  protected val db: Database = app.injector.instanceOf[Database]
  protected val host: String = s"http://localhost:$port"

  before {
    createUser()
  }

  after {
    db.withConnection { implicit conn: Connection =>
      SQL("TRUNCATE TABLE users CASCADE").execute()
    }
  }

  protected def createUser(): Unit = { userRepository.create(A_USER_ID, AN_EMAIL, A_PASSWORD) }
}
