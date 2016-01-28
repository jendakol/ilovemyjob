package cz.jenda.ilovemyjob

import scala.annotation.tailrec

/**
  * @author Jenda Kolena, jendakolena@gmail.com
  */
object Formatter {

  def formatJsonMessage(message: String): String = {
    message.replaceAll("\\r?\\n", "; ").replaceAll("  +", " ").replaceAll(" :", ":").replaceAll(" \\}", "}").replaceAll("\\{ ", "{")
  }

  @inline
  def formatException(e: Throwable) = e.getClass.getName + ": " + e.getMessage

  def formatExceptionWithCause(e: Throwable): String = {
    val builder = new StringBuilder

    builder.append("(" + formatException(e) + ")")

    @tailrec
    def loop(cause: Throwable, builder: StringBuilder): StringBuilder = {
      if (cause != null) {
        builder.append(" -> (" + formatException(cause) + ")")
        loop(cause.getCause, builder)
      } else {
        builder
      }
    }

    loop(e.getCause, builder).toString()
  }

}
