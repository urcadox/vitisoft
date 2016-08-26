package controllers

import play.api.mvc._
import play.api.mvc.Results._

object Errors {

  def notFound()(implicit request: Request[AnyContent]) = NotFound(views.html.errors.notFound())

}
