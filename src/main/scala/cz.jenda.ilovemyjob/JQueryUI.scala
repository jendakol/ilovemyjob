package cz.jenda.ilovemyjob

import org.scalajs.jquery.{JQueryEventObject, JQuery}

import scala.language.implicitConversions
import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

/**
  * @author Jenda Kolena, jendakolena@gmail.com
  */
@js.native
trait JQueryUI extends JQuery {
  def sortable(options: SortableSettings): js.Any = js.native
}

@js.native
object JQueryUI {
  //noinspection NotImplementedCode
  @JSName("jQuery")
  def jQueryUI: JQueryUI = ???

  implicit def jQueryUIExtensions(query: JQuery): JQueryUI =
    query.asInstanceOf[JQueryUI]
}

@js.native
trait SortableSettings extends js.Object {
  def stop(event: JQueryEventObject, ui: js.Any): js.Any = js.native
}
