package cz.jenda.ilovemyjob

import org.scalajs.jquery.jQuery

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport

/**
  * @author Jenda Kolena, jendakolena@gmail.com
  */
object Main extends js.JSApp {

  import createjs._

  private val sounds = Map(
    "Abys mohl toto!" -> "abys-mohl-toto",
    "Ani očko nenasadíš!" -> "ani-ocko-nenasadis",
    "Ani za kokot, vole!" -> "ani-za-kokot-vole",
    "Banální věc..." -> "banalni-vec",
    "Největší blbec na zeměkouli" -> "nejvetsi-blbec-na-zemekouli",
    "Do píče!" -> "do-pice",
    "Hajzli jedni!" -> "hajzli-jedni",
    "Hoši, to je neuvěřitelné!" -> "hosi-to-je-neuveritelne",
    "Já se z toho musím pojebat!" -> "ja-se-z-toho-musim-pojebat",
    "Já to mrdám!" -> "ja-to-mrdam",
    "Já to nejdu dělat!" -> "ja-to-nejdu-delat",
    "Jedinou pičovinku..." -> "jedinou-picovinku",
    "Jedu do piče z tadyma!" -> "jedu-do-pici-stadyma",
    "To není možné!" -> "kurva-do-pice-to-neni-mozne",
    "Kurva!" -> "kurva",
    "Kurva už!" -> "kurva-uz",
    "Nebudu to dělat!" -> "nebudu-to-delat",
    "Nenasadím!" -> "nenasadim",
    "Ne, nenasadíš ho!" -> "ne-nenasadis-ho",
    "Neřešitelný problém, hoši!" -> "neresitelny-problem-hosi",
    "Nevím jak" -> "nevim-jak",
    "Okamžitě zabít ty kurvy!" -> "okamzite-zabit-ty-kurvy",
    "Past vedle pasti, pičo!" -> "past-vedle-pasti-pico",
    "Počkej kámo..." -> "pockej-kamo",
    "Tady všechno musíš rozdělat!" -> "tady-musis-vsechno-rozdelat",
    "To je pičo nemožné!" -> "to-je-pico-nemozne",
    "To není normální, kurva!" -> "to-neni-normalni-kurva",
    "To sou nervy, ty pičo!" -> "to-sou-nervy-ty-pico",
    "Tuto piču potřebuju utáhnout" -> "tuto-picu-potrebuju-utahnout",
    "Zasrané, zamrdané" -> "zasrane-zamrdane"
  )

  def main(): Unit = {
    sounds.foreach { case (title, soundName) =>
      //register sound
      Sound.registerSound(s"sfx/$soundName.mp3", soundName)

      //create button
      jQuery("#main").append(button(title, soundName))
    }
  }

  private def button(title: String, id: String): String =
    s"""
       |<div class="button" onclick='ILoveMyJob.play("$id")'>$title</div>
     """.stripMargin

  @JSExport
  def play(id: String): Unit = {
    println(s"Playing '$id")
    Sound.stop()
    Sound.play(id)
  }
}