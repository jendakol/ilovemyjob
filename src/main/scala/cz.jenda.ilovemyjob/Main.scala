package cz.jenda.ilovemyjob

import java.util.Date
import java.util.concurrent.TimeUnit

import cz.jenda.ilovemyjob.Cookies.Cookie
import cz.jenda.ilovemyjob.JQueryUI._
import org.scalajs.dom
import org.scalajs.jquery.{JQueryEventObject, jQuery}

import scala.concurrent.Future
import scala.scalajs.js
import scala.scalajs.js.Any
import scala.scalajs.js.annotation.JSExport
import scala.util.Success
import Implicits._
/**
  * @author Jenda Kolena, jendakolena@gmail.com
  */
object Main extends js.JSApp {

  implicit val executionContext = scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  import createjs._

  private val sounds = Map(
    "abys-mohl-toto" -> "Abys mohl toto!",
    "ani-ocko-nenasadis" -> "Ani očko nenasadíš!",
    "ani-za-kokot-vole" -> "Ani za kokot, vole!",
    "banalni-vec" -> "Banální věc...",
    "nejvetsi-blbec-na-zemekouli" -> "Největší blbec na zeměkouli",
    "do-pice" -> "Do píče!",
    "hajzli-jedni" -> "Hajzli jedni!",
    "hosi-to-je-neuveritelne" -> "Hoši, to je neuvěřitelné!",
    "ja-se-z-toho-musim-pojebat" -> "Já se z toho musím pojebat!",
    "ja-to-mrdam" -> "Já to mrdám!",
    "ja-to-nejdu-delat" -> "Já to nejdu dělat!",
    "jedinou-picovinku" -> "Jedinou pičovinku...",
    "jedu-do-pici-stadyma" -> "Jedu do piče z tadyma!",
    "kurva-do-pice-to-neni-mozne" -> "To není možné!",
    "kurva" -> "Kurva!",
    "kurva-uz" -> "Kurva už!",
    "nebudu-to-delat" -> "Nebudu to dělat!",
    "nenasadim" -> "Nenasadím!",
    "ne-nenasadis-ho" -> "Ne, nenasadíš ho!",
    "neresitelny-problem-hosi" -> "Neřešitelný problém, hoši!",
    "nevim-jak" -> "Nevím jak",
    "okamzite-zabit-ty-kurvy" -> "Okamžitě zabít ty kurvy!",
    "past-vedle-pasti-pico" -> "Past vedle pasti, pičo!",
    "pockej-kamo" -> "Počkej kámo...",
    "tady-musis-vsechno-rozdelat" -> "Tady všechno musíš rozdělat!",
    "to-je-pico-nemozne" -> "To je pičo nemožné!",
    "to-neni-normalni-kurva" -> "To není normální, kurva!",
    "to-sou-nervy-ty-pico" -> "To sou nervy, ty pičo!",
    "tuto-picu-potrebuju-utahnout" -> "Tuto piču potřebuju utáhnout",
    "zasrane-zamrdane" -> "Zasrané, zamrdané"
  )

  def main(): Unit = {
    init()

    getLocationHash
      .flatMap { id =>
        sounds.keySet.find(_ == id)
      }.foreach { idToPlay =>
      Sound.on("fileload", (e: Event) => {
        val id = e.src.split("[\\./]")(1)
        if (id == idToPlay) play(id)

        anyUnit
      })
    }
  }

  private def getLocationHash: Option[String] = {
    Option(dom.location.hash)
      .map { s =>
        s.trim.substring(1) //strip start #
      }.filter(_.length > 0)
  }

  private def init(): Unit = {
    val ordered = Cookies.getAll
      .find(_.key == "order")
      .map { c =>
        val order = c.value.split("\\|").toSeq

        order.map { id =>
          (id, sounds(id))
        }
      }.getOrElse(sounds.toSeq)

    ordered.foreach { case (soundName, title) =>
      //register sound
      Sound.registerSound(s"sfx/$soundName.mp3", soundName)

      //create button
      jQuery("#main").append(createButton(title, soundName))
    }

    jQuery("#main").sortable(js.Dynamic.literal(
      stop = { (event: JQueryEventObject, ui: Any) => {
        saveNewOrder
      }
      }
    ))
  }

  private val yearMillis = TimeUnit.MILLISECONDS.convert(365, TimeUnit.DAYS)

  private def saveNewOrder: Future[AjaxCallback] = {
    val order = collectButtonsOrder().mkString("|")

    Cookies.create(Cookie("order", order), new Date(System.currentTimeMillis() + yearMillis))

    Utils.ajax('save, s"{'order': '$order'}").andThen { case Success(AjaxCallback(data, status, _)) =>
      println("New order saved")
    }
  }

  private def collectButtonsOrder(): Seq[String] = {
    jQuery("#main div.button")
      .toArray().toSeq.map(_.asInstanceOf[dom.Element])
      .map { elem =>
        elem.id
      }
  }

  private def createButton(title: String, id: String): String = {
    s"""<div class="button" id="$id" onclick='ILoveMyJob.play("$id")'>$title</div>"""
  }

  @JSExport
  def play(id: String): Unit = {
    println(s"Playing '$id")
    Sound.stop()
    Sound.play(id)

    dom.location.hash = id
  }

  private val anyUnit: js.Any = {
    import Any.fromUnit

    fromUnit(()) //implicit conversion doesn't work over 2 levels
  }
}
