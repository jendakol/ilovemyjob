package cz.jenda.ilovemyjob

import org.scalajs.jquery.JQueryAjaxSettings

import scala.language.implicitConversions
import scala.scalajs.js

/**
  * @author Jenda Kolena, jendakolena@gmail.com
  */
object Implicits {
  implicit def DynamicToSortableSettings(d: js.Dynamic): SortableSettings = d.asInstanceOf[SortableSettings]

  implicit def DynamicToAjaxSettings(d: js.Dynamic): JQueryAjaxSettings = d.asInstanceOf[JQueryAjaxSettings]
}
