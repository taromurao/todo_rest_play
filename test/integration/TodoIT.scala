package integration

import org.scalatest.BeforeAndAfterAll
import play.api.libs.json.{JsArray, JsObject, JsString}
import play.api.libs.ws._
import play.api.test.Helpers._

import scala.concurrent.Await
import scala.concurrent.duration._

class TodoIT extends BaseIT with BeforeAndAfterAll{

  "A user" must{
    "be able to create a todos and view them" in {
      createTodo(todo1).status mustBe CREATED

      createTodo(todo2).status mustBe CREATED

      val response: WSResponse = getTodos()
      response.status mustBe OK

      (response.json \ "data").as[Set[JsObject]] mustBe Set(todo1, todo2)
    }
  }

  private val uri: String = s"$host/users/$A_USER_ID/todos"

  private val request: WSRequest = wsClient
    .url(uri)
    .addHttpHeaders("Content-Type" -> "application/json")

  private def createTodo(todo: JsObject): WSResponse = Await.result(request.post(todo), 1000 millis)

  private def getTodos(): WSResponse = Await.result(request.get(), 1000 millis)

  private val todo1: JsObject = JsObject(Seq(
    "title" -> JsString("a todo"),
    "content" -> JsString("Do this.")
  ))

  private val todo2: JsObject = JsObject(Seq(
    "title" -> JsString("another todo"),
    "content" -> JsString("This is a tough one.")
  ))

  //  override def beforeAll: Unit = createUser()

  //  "Unauthorised request" must{
  //    "be rejected" in{
  //      val uri: String = s"$host/users/$AN_EMAIL/todos"
  //      val request: WSRequest = wsClient
  //        .url(uri)
  //        .addHttpHeaders("Content-Type" -> "application/json")
  //      val response: WSResponse = Await.result(request.get(), 1000 millis)
  //
  //      response.status mustBe UNAUTHORIZED
  //    }
  //  }
}
