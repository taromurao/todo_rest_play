package controllers

import models.User
import org.scalatest.mockito.MockitoSugar

import language.postfixOps
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json.{JsString, Json}
import play.api.test._
import play.api.test.Helpers._
import repositories.UserRepository

class UserControllerSpec extends PlaySpec with GuiceOneAppPerSuite with MockitoSugar {
  "UserController POST" must {
    "create a new user if data does not consist an id" in {
      val userRepository: UserRepository = mock[UserRepository]{
        def save(user: User) = user.id
      }

      val controller: UserController = new UserController(stubControllerComponents(), userRepository)
       val json = Json.obj(
            "email" -> JsString("john@example.com"),
            "password" -> JsString("1234567890")
       )
      val result = controller
        .create()(
          FakeRequest(
            method = POST,
            uri = "/users",
            headers = FakeHeaders(Seq(("Content-type", "application/json"))),
            body = json
          ))

      status(result) mustBe CREATED
      contentType(result) mustBe Some("application/json")
//      contentAsString(result) must include("Welcome to Play")
    }
  }
}
