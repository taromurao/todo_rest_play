package controllers

import javax.inject._

import play.api.libs.json.Json
import play.api.mvc._

@Singleton
class TodoController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(Json.toJson("hello"))
  }
}
