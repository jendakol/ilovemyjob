package cz.jenda.ilovemyjob

import org.scalajs.jquery.jQuery

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport

/**
  * @author Jenda Kolena, jendakolena@gmail.com
  */
object Main extends js.JSApp {

  import createjs._

  private val sounds = Seq(
    "abys-mohl-toto",
    "ani-ocko-nenasadis",
    "ani-za-kokot-vole",
    "banalni-vec",
    "do-pice",
    "hajzli-jedni",
    "hosi-to-je-neuveritelne",
    "ja-se-z-toho-musim-pojebat",
    "ja-to-mrdam",
    "ja-to-nejdu-delat",
    "jedinou-picovinku",
    "jedu-do-pici-stadyma",
    "kurva-do-pice-to-neni-mozne",
    "kurva",
    "kurva-uz",
    "nebudu-to-delat",
    "nejvetsi-blbec-na-zemekouli",
    "nenasadim",
    "ne-nenasadis-ho",
    "neresitelny-problem-hosi",
    "nevim-jak",
    "okamzite-zabit-ty-kurvy",
    "past-vedle-pasti-pico",
    "pockej-kamo",
    "tady-musis-vsechno-rozdelat",
    "to-je-pico-nemozne",
    "to-neni-normalni-kurva",
    "to-sou-nervy-ty-pico",
    "tuto-picu-potrebuju-utahnout",
    "zasrane-zamrdane"
  )

  def main(): Unit = {
    sounds.foreach { soundName =>
      //register sound
      Sound.registerSound(s"sfx/$soundName.mp3", soundName)

      //create button
      jQuery("body").append(s"""<span onclick='ILoveMyJob.play("$soundName")'>$soundName</span><br>""")
    }
  }

  @JSExport
  def play(id: String): Unit = {
    println(s"Playing '$id")
    Sound.stop()
    Sound.play(id)
  }
}