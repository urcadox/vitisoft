package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import scala.concurrent.ExecutionContext
import models.entities.{Plot, PlotDAO}

@Singleton
class PlotController @Inject() (
  val plotDAO: PlotDAO,
  implicit val ec: ExecutionContext
) extends Controller {

  def plots = Action.async { implicit request =>
    plotDAO.getAll map { plots =>
      Ok(views.html.plots(plots))
    }
  }

  def plot(id: Plot.PlotID) = Action.async { implicit request =>
    plotDAO.getById(id) map { plotOption =>
      plotOption map { plot =>
        Ok(views.html.plot(plot))
      } getOrElse Errors.notFound()
    }
  }

}
