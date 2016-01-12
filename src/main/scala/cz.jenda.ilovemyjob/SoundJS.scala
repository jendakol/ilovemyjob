package cz.jenda.ilovemyjob

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

/**
  * @author Jenda Kolena, jendakolena@gmail.com
  */
@js.native
@JSName("createjs.Sound")
trait SoundJS extends js.Object {
  def registerSound(path: String, id: String): Unit = js.native

  def play(id: String): Unit = js.native

  def stop(): Unit = js.native

  def on(event: String, handler: js.Function1[Event, js.Any]): Unit = js.native
}

@js.native
trait CreateJs extends js.Object {
  val Sound: SoundJS = js.native
}

@js.native
@JSName("createjs.Event")
trait Event extends js.Object {
  def id: String

  def src: String

  def data: String

  def sprite: String
}
