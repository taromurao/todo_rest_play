package controllers

import models._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json._
import play.api.mvc.Result
import play.api.test._
import play.api.test.Helpers._
import repositories.UserRepository

import scala.concurrent.Future

class UserControllerSpec extends PlaySpec with MockitoSugar {
  "UserController POST" must {
    "create a new user" in {
      val userRepository = mock[UserRepository]
      val controller: UserController = new UserController(userRepository, stubControllerComponents())
      val json = JsObject(
        Seq(
          "email" -> JsString("john@example.com"),
          "password" -> JsString("1234567890")
      ))

      val result: Future[Result] = controller
        .create()(
          FakeRequest(
            method = POST,
            uri = "/users",
            headers = FakeHeaders(Seq(("Content-type", "application/json"))),
            body = json
          ))

      status(result) mustBe CREATED
      contentType(result) mustBe Some("application/json")
      contentAsJson(result) mustBe JsObject(
        Seq("data" -> JsObject(Seq("response" -> JsString("created")))))
    }
  }
}

