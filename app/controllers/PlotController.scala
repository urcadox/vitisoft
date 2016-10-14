package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import scala.concurrent.ExecutionContext
import models.entities.{Plot, PlotDAO, PlotAudit}
import play.api.i18n._

@Singleton
class PlotController @Inject() (
  val plotDAO: PlotDAO,
  implicit val ec: ExecutionContext,
  val messagesApi: MessagesApi
) extends Controller with I18nSupport {

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

  def audit(plotId: Plot.PlotID, auditId: PlotAudit.PlotAuditID) = Action.async { implicit request =>
    plotDAO.getById(plotId) map { plotOption =>
      plotOption map { plot =>
        plot.audits.find(_.id == auditId) map {audit =>
          Ok(views.html.plotaudit(plot, audit))
        } getOrElse Errors.notFound()
      } getOrElse Errors.notFound()
    }
  }
}
