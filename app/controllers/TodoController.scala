package controllers

import java.util.UUID
import javax.inject._

import models.Todo
import play.api.Logger
import play.api.libs.json._
import play.api.mvc._
import play.api.libs.functional.syntax._

import repositories.{TodoRepository, UserRepository}

import scala.util.{Success, Try}

@Singleton
class TodoController @Inject()(
                                todoRepository: TodoRepository,
                                userRepository: UserRepository,
                                cc: ControllerComponents) extends AbstractController(cc) {

  private val BAD_REQUEST_RESPONSE = BadRequest(JsObject(Seq("response" -> JsString("failed"))))

  def index(userId: UUID) = Action { implicit request =>
    Logger.info(s"Executing TodoController.index")
    userRepository.get(userId) match {
      case Some(user) => {
        Logger.info(s"Got user ${user}")
        Try(todoRepository.ofUser(user)) match {
          case Success(xs) => Ok(JsObject(Seq("response" -> JsString("ok"), "data" -> Json.toJson(xs))))
          case _ => InternalServerError(JsObject(Seq("response" -> JsString("Database error"))))
        }
      }
      case _ => BAD_REQUEST_RESPONSE
    }
  }

  def create(userId: UUID): Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    userRepository.get(userId) match {
      case Some(user) => {
        request.body.validate[(String, String)] map {
          case (title, content) => {
            todoRepository.save(user, title, content)
            Created(JsObject(Seq("response" -> JsString("created"))))
          }
          case _ => BAD_REQUEST_RESPONSE
        } recoverTotal { _ => BAD_REQUEST_RESPONSE }
      }
      case _ => BAD_REQUEST_RESPONSE
    }
  }

  def update(userId: UUID, todoId: UUID): Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    userRepository.get(userId) match {
      case Some(user) => {
        request.body.validate[Todo] map {
          case t @ Todo(_, _, _) => {
            todoRepository.update(user, t)
            Ok(JsObject(Seq("response" -> JsString("updated"))))
          }
          case _ => BAD_REQUEST_RESPONSE
        } recoverTotal { _ => BAD_REQUEST_RESPONSE }
      }
      case _ => BAD_REQUEST_RESPONSE
    }
  }

  implicit private val titleContentReads: Reads[(String, String)] = (
    (__ \ 'title).read[String] and
    (__ \ 'content).read[String]
  ) tupled
}
