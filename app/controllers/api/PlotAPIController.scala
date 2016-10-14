package controllers.api

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}
import models.entities.{Plot, PlotDAO, PlotInput}

@Singleton
class PlotAPIController @Inject() (
  val plotDAO: PlotDAO,
  implicit val ec: ExecutionContext
) extends Controller {

  def plots = Action.async { implicit request =>
    plotDAO.getAll map { plots =>
      Ok(Json.toJson(plots.map(p => p.copy(audits = p.sortedAudits.reverse))))
    }
  }

  def plot(id: Plot.PlotID) = Action.async { implicit request =>
    plotDAO.getById(id) map { plotOption =>
      plotOption map { plot =>
        Ok(Json.toJson(plot.copy(audits = plot.sortedAudits.reverse)))
      } getOrElse NotFound
    }
  }

  def editPlot(id: Plot.PlotID) = Action.async(parse.json) { implicit request =>
    request.body.validate[PlotInput].fold(
      e => Future.successful(BadRequest(JsError.toJson(e))),
      plot => plotDAO.update(id, plot).map(plot => Ok(Json.toJson(plot)))
    )
  }

}
