package integration

import java.util.UUID

import models.Email
import org.scalatest.BeforeAndAfterAll
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.libs.json._
import play.api.libs.ws._
import play.api.test.Helpers._

import scala.concurrent.Await
import scala.concurrent.duration._

abstract class BaseIT extends PlaySpec with GuiceOneServerPerSuite with BeforeAndAfterAll{

  val A_PASSWORD = "1234567890"
  val AN_EMAIL: Email = "john@example.com"
  val A_USER_ID: UUID = UUID.fromString("d824a403-d159-4dcd-966c-0096274cd612")

  protected val wsClient: WSClient = app.injector.instanceOf[WSClient]
  protected val host: String = s"http://localhost:$port"


  protected def createUser(): Unit = {
    val data: JsObject = JsObject(Seq(
      "email" -> JsString(AN_EMAIL),
      "password" -> JsString(A_PASSWORD)))
    val uri = s"$host/users"
    val request: WSRequest = wsClient
      .url(uri)
      .addHttpHeaders("Content-Type" -> "application/json")
      .withRequestTimeout(10000 millis)

    val response: WSResponse = Await.result(request.post(data), 1000 millis)
    response.status mustBe CREATED
  }
}
