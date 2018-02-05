package controllers

import javax.inject.{Inject, Singleton}

import models._
import play.api.mvc._
import repositories.UserRepository
import play.api.libs.json._
import play.api.libs.functional.syntax._

@Singleton
class UserController @Inject()(
                                userRepository: UserRepository,
                                cc: ControllerComponents
                              ) extends AbstractController(cc) {

  val OK_RESPONSE_JSON = JsObject(Seq("data" -> JsObject(Seq("response" -> JsString("created")))))
  val INVALID_RESPONSE_JSON = JsObject(Seq("data" -> JsObject(Seq("response" -> JsString("invalid")))))

  implicit val rds = (
    (__ \ 'email).read[Email] and
    (__ \ 'password).read[String]
  ) tupled

  def create: Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[(Email, String)] map {
      case (email, password) => {
        userRepository.create(email, password)
        Created(OK_RESPONSE_JSON)
      }
      case _ => BadRequest(INVALID_RESPONSE_JSON)
    } recoverTotal { _ => BadRequest(INVALID_RESPONSE_JSON) }
  }
}
