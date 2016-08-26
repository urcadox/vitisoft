package controllers

import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class PlotController @Inject() extends Controller {

  def plots = Action { implicit request =>
    Ok(views.html.plots())
  }

}
