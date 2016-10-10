package controllers

import play.api.mvc._
import play.api.mvc.Results._

/**
 *  Holds custom error pages to be used as html results
 */
object Errors {

  /** Returns a HTML custom 404 error page */
  def notFound()(implicit request: Request[AnyContent]) = NotFound(views.html.errors.notFound())

}
