package controllers

import java.util.UUID
import javax.inject._

import models.Todo
import play.api.libs.json._
import play.api.mvc._
import repositories.{TodoRepository, UserRepository}

@Singleton
class TodoController @Inject()(
                                todoRepository: TodoRepository,
                                userRepository: UserRepository,
                                cc: ControllerComponents) extends AbstractController(cc) {

  private val BAD_REQUEST_RESPONSE = BadRequest(JsObject(Seq("response" -> JsString("failed"))))

  def index(userId: UUID): Action[JsValue] = Action(parse.json) { implicit request =>
    userRepository.get(userId) match {
      case Some(user) => Ok(JsObject(Seq(
        "response" -> JsString("ok"),
        "data" -> Json.toJson(todoRepository.ofUser(user))
      )))
      case _ => BAD_REQUEST_RESPONSE
    }
  }

  def create(userId: UUID): Action[JsValue] = Action(parse.json) { implicit request =>
    userRepository.get(userId) match {
      case Some(user) => {
        request.body.validate[Todo] map {
          case todo @ Todo(_, _) => {
            todoRepository.save(user, todo)
            Created(JsObject(Seq("response" -> JsString("created"))))
          }
          case _ => BAD_REQUEST_RESPONSE
        } recoverTotal { _ => BAD_REQUEST_RESPONSE }
      }
      case _ => BAD_REQUEST_RESPONSE
    }
  }
}
