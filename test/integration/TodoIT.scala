package integration

import java.util.UUID

import models.Todo
import org.scalatest.BeforeAndAfterAll
import play.api.libs.ws._
import play.api.libs.json._
import play.api.test.Helpers._

import scala.collection.immutable.Set
import scala.concurrent.Await
import scala.concurrent.duration._
import testHelpers._

class TodoIT extends BaseIT with BeforeAndAfterAll{

  "A user" must{
    "be able to CRUD " in {
      createTodo(todo1).status mustBe CREATED

      createTodo(todo2).status mustBe CREATED

      val response: WSResponse = getTodos()

      response.status mustBe OK

      val todos: Set[Todo] = (response.json \ "data").validate[Set[Todo]].get
      val titles: Set[String] = todos map (_.title)
      val contents: Set[String] = todos map (_.content)

      todos.size mustBe 2

      titles must contain (todo1.title)

      titles must contain (todo2.title)

      contents must contain (todo1.content)

      contents must contain (todo2.content)

      val todo1id: UUID = todos.find { t => t.title == todo1.title }.get.id
      val responseUpdate: WSResponse = updateTodo(Todo(todo1id, todo1update.title, todo1update.content))
      responseUpdate.status mustBe OK

      val responseUpdated: WSResponse = getTodos()
      val todosUpdated: Set[Todo] = (responseUpdated.json \ "data").validate[Set[Todo]].get
      val titlesUpdated: Set[String] = todosUpdated map (_.title)
      val contentsUpdated: Set[String] = todosUpdated map (_.content)

      titlesUpdated must contain (todo1update.title)

      contentsUpdated must contain (todo1update.content)
    }
  }

  private val uri: String = s"$host/users/$A_USER_ID/todos"

  private def request: WSRequest = request(None)

  private def request(id: Option[UUID]) = wsClient
    .url(uri + id.map { x: UUID => s"/$x"}.getOrElse(""))
    .addHttpHeaders("Content-Type" -> "application/json")

  private def createTodo(todo: Todo): WSResponse = {
    val json: JsObject =
      JsObject(Seq("title" -> JsString(todo.title), "content" -> JsString(todo.content)))

    Await.result(request.post(json), 1000 millis)
  }

  private def getTodos(): WSResponse = Await.result(request.get(), 1000 millis)

  private def updateTodo(todo: Todo): WSResponse = {
    val json: JsValue = Json.toJson(todo)

    Await.result(request(Some(todo.id)).patch(json), 1000 millis)
  }
}
