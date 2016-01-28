package cz.jenda.ilovemyjob

import java.util.Date

import org.scalajs.dom
import org.scalajs.jquery._
import upickle.default._

import scala.concurrent.{Future, Promise}
import scala.scalajs.js
import scala.util.{Failure, Try}

/**
  * @author Jenda Kolena, jendakolena@gmail.com
  */
object Utils {

  import Main.executionContext

  def ajax(action: Symbol, data: String = "{}"): Future[AjaxCallback] = {
    val p = Promise[AjaxCallback]

    jQuery.ajax(js.Dynamic.literal(
      `type` = "POST",
      url = "ajax.html?action=" + action.name,
      data = data,
      dataType = "text",
      timeout = 3000,
      success = { (data: js.Any, textStatus: String, jqXHR: JQueryXHR) =>
        p.complete(Try {
          val stringData = data.toString

          val ajaxResult = read[AjaxResult](stringData)
          if (ajaxResult.status != "ok") throw AjaxException(ajaxResult.status, "invalid status", jqXHR)

          AjaxCallback(stringData, textStatus, jqXHR)
        })
      },
      error = { (jqXHR: JQueryXHR, status: String, error: String) => {
        p.failure(AjaxException(status, error, jqXHR))
      }
      }
    ).asInstanceOf[JQueryAjaxSettings])

    p.future.andThen {
      case Failure(e) => println(Formatter.formatExceptionWithCause(e))
    }
  }
}

case class AjaxException(status: String, desc: String, xhr: JQueryXHR) extends RuntimeException(s"status=$status, error=$desc")

case class AjaxCallback(data: String, status: String, xhr: JQueryXHR)

private case class AjaxResult(status: String)

object Cookies {
  def create(cookie: Cookie, expires: Date): Unit = {
    println(s"Saving $cookie")

    save(cookie, expires)
  }

  def getAll: Seq[Cookie] = {
    val raw = dom.document.cookie
    if (raw.length == 0) return Seq()

    raw.split(";").toSeq
      .map(_.trim)
      .map(Cookie.apply)
  }

  private def save(cookie: Cookie, expires: Date): Unit = {

    val exp = "; expires=" + new js.Date(expires.getTime).toUTCString()

    dom.document.cookie = s"""${cookie.key}=${cookie.value}$exp"""
  }

  case class Cookie(key: String, value: String)

  object Cookie {
    private[Cookies] def apply(t: String): Cookie = {
      var parts = t.split(",").map(_.trim)

      val keyValue = parts.headOption.map(_.split("=")).getOrElse(Array("unknown", "unknown"))
      parts = parts.drop(1)

      Cookie(keyValue(0), keyValue(1))
    }
  }

}
