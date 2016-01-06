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
}

@js.native
trait CreateJs extends js.Object {
  val Sound: SoundJS = js.native
}
