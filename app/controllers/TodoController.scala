package controllers

import java.util.UUID
import javax.inject._

import play.api.libs.json.{JsArray, JsObject}
import play.api.mvc._

@Singleton
class TodoController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index(userId: UUID) = Action { implicit request: Request[AnyContent] =>
    Ok(JsObject(Seq("data" -> JsArray())))
  }

  def create(userId: UUID) = Action { implicit request: Request[AnyContent] =>
    Created(JsObject(Seq()))
  }
}
