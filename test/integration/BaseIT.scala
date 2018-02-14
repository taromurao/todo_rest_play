package integration

import java.util.UUID

import models.Email
import org.scalatest.BeforeAndAfterAll
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.libs.ws._
import repositories.UserRepository

abstract class BaseIT extends PlaySpec with GuiceOneServerPerSuite with BeforeAndAfterAll{

  val A_PASSWORD = "1234567890"
  val AN_EMAIL: Email = "john@example.com"
  val A_USER_ID: UUID = UUID.fromString("d824a403-d159-4dcd-966c-0096274cd612")

  protected val wsClient: WSClient = app.injector.instanceOf[WSClient]
  protected val host: String = s"http://localhost:$port"


  override def beforeAll() {
    createUser()
  }

  protected def createUser(): Unit = {
    val userRepository: UserRepository = app.injector.instanceOf[UserRepository]
    userRepository.create(A_USER_ID, AN_EMAIL, A_PASSWORD)
  }
}
