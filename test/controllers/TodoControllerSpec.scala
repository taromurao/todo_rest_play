package controllers

import models.Todo
import org.scalatest.BeforeAndAfterAll
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._
import org.mockito.Mockito._
import play.api.libs.json.JsObject
import play.api.mvc.Result
import repositories.{TodoRepository, UserRepository}
import testHelpers._

import scala.concurrent.Future

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class TodoControllerSpec extends PlaySpec with BeforeAndAfterAll with MockitoSugar{
  private val userRepository: UserRepository = mock[UserRepository]
  private val todoRepository: TodoRepository = mock[TodoRepository]
  private val controller = new TodoController(todoRepository, userRepository, stubControllerComponents())

  override def beforeAll(): Unit = {
    when(userRepository.get(A_USER_ID)) thenReturn Some(A_USER)
    when(todoRepository.ofUser(A_USER)) thenReturn Set(todo1, todo2)
  }

  "TodoController GET" must {
    "render the index page" in {
      val request = FakeRequest(
        method = GET,
        uri = s"/users/$A_USER_ID/todos",
        headers = FakeHeaders(Seq("Content-Type" -> "application/json")),
        body = JsObject(Seq()))
      val result: Future[Result] = controller.index(A_USER_ID)(request)

      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
      (contentAsJson(result) \ "data").validate[Set[Todo]].getOrElse(None) mustBe Set(todo1, todo2)
    }
//
//    "render the index page from the application" in {
//      val controller = inject[TodoController]
//      val home = controller.index().apply(FakeRequest(GET, "/"))
//
//      status(home) mustBe OK
//      contentType(home) mustBe Some("text/html")
//      contentAsString(home) must include ("Welcome to Play")
//    }
//
//    "render the index page from the router" in {
//      val request = FakeRequest(GET, "/")
//      val home = route(app, request).get
//
//      status(home) mustBe OK
//      contentType(home) mustBe Some("text/html")
//      contentAsString(home) must include ("Welcome to Play")
//    }

//    private def createUser(): Unit = {
//      val todoRepository: TodoRepository = new TodoRepository(stubControllerComponents()) {}
//      todoRepository.save()
//    }
  }
}
