package controllers

import models.Todo
import org.scalatest.BeforeAndAfterAll
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._
import org.mockito.Mockito._
import play.api.libs.json.Json
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
    when(todoRepository.ofUser(A_USER)) thenReturn List(todo1, todo2)
  }

  "TodoController GET" must {
    "render the index page" in {
      val request = FakeRequest(GET, s"/users/$A_USER_ID/todos")
      val result: Future[Result] = controller.index(A_USER_ID)(request)

      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
      (contentAsJson(result) \ "data").validate[List[Todo]].get.toSet mustBe Set(todo1, todo2)
    }
  }

  "TodoController POST" must {
    "create a todo" in {
      val request = FakeRequest(
        method = POST,
        uri = s"/users/$A_USER_ID/todos",
        headers = FakeHeaders(Seq("Content-Type" -> "application/json")),
        body = Json.toJson(todo1)
      )
      val result: Future[Result] = controller.create(A_USER_ID)(request)

      status(result) mustBe CREATED
      contentType(result) mustBe Some("application/json")
      verify(todoRepository, times(1)).save(A_USER, todo1.title, todo1.content)
    }
  }

//    private def createUser(): Unit = {
//      val todoRepository: TodoRepository = new TodoRepository(stubControllerComponents()) {}
//      todoRepository.save()
//    }
}
