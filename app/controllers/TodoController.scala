package controllers

import java.util.UUID
import javax.inject._

import play.api.libs.json._
import play.api.mvc._
import repositories.{TodoRepository, UserRepository}

@Singleton
class TodoController @Inject()(
                                todoRepository: TodoRepository,
                                userRepository: UserRepository,
                                cc: ControllerComponents) extends AbstractController(cc) {

  def index(userId: UUID): Action[JsValue] = Action(parse.json) { implicit request =>
    userRepository.get(userId) match {
      case Some(user) => Ok(JsObject(Seq(
        "response" -> JsString("ok"),
        "data" -> Json.toJson(todoRepository.ofUser(user))
      )))
      case _ => NotFound(JsObject(Seq("response" -> JsString("failed"))))
    }
  }

  def create(userId: UUID): Action[JsValue] = Action(parse.json) { implicit request =>
    Created(JsObject(Seq()))
  }
}
