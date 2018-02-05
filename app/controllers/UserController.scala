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

  val CREATED_STRING = "created"
  val INVALID_STRING = "invalid input"

  implicit val rds: Reads[(Email, String)] = (
    (__ \ 'email).read[Email] and
    (__ \ 'password).read[String]
  ) tupled

  def create: Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[(Email, String)] map {
      case (email, password) => {
        userRepository.create(email, password)
        Created(jsResponse(JsString(CREATED_STRING)))
      }
      case _ => BadRequest(JsString(INVALID_STRING))
    } recoverTotal { _ => BadRequest(JsString(INVALID_STRING)) }
  }

  private def jsResponse(data: JsValue): JsObject = JsObject(Seq("data" -> JsObject(Seq("response" -> data))))
}
