package controllers

import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import play.api.libs.json._
import play.api.mvc.Result
import play.api.test._
import play.api.test.Helpers._
import repositories.UserRepository

import scala.concurrent.Future

import testHelpers._

class UserControllerSpec extends PlaySpec with MockitoSugar{
  private val userRepository = mock[UserRepository]
  private val controller: UserController = new UserController(userRepository, stubControllerComponents())

  "UserController POST" must {
    "create a new user" in {
      val json = JsObject(
        Seq(
          "email" -> JsString(AN_ENAIL),
          "password" -> JsString(A_PASSWORD)
      ))
      val request = FakeRequest(
          method = POST,
          uri = "/users",
          headers = FakeHeaders(Seq(("Content-Type", "application/json"))),
          body = json)
      val result: Future[Result] = controller.create()(request)

      status(result) mustBe CREATED
      contentType(result) mustBe Some("application/json")
      contentAsJson(result) mustBe JsObject(
        Seq("data" -> JsObject(Seq("response" -> JsString("created")))))
    }
  }
}

