package controllers

import javax.inject.{Inject, Singleton}

import play.api.mvc._
import repositories.UserRepository

@Singleton
class UserController @Inject()(
                                cc: ControllerComponents,
                                val userRepository: UserRepository
                              ) extends AbstractController(cc) {
  def create(): Action[AnyContentAsJson] = Action(parse.json) { request =>
//    val request.body.validate[User]
  }
}
