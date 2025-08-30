package tst


import scala.util.Try
import scala.util.Using
import tst.models.Rate
import tst.models.CabinPrice


object Readers {

  /**
   * Read and parse from a file.
   * This function expects each line to be a single point of data to be parsed
   * using the supplied function.
   *
   * Errors parsing are represented by an empty parse result and will be skipped
   * in the resulting list
   *
   * Errors reading from the file and other general exceptions are captured in
   * the wrapping [[Try]]
   *
   * @param filename Name of the file to read
   * @param parser Function to convert one line of text into the resulting type
   * @return [[Seq]] with all the successfully read lines converted
   */
  private def dataReader[U](
      filename: String,
      parser: String => Option[U]
  ): Try[Seq[U]] =
    Using(scala.io.Source.fromFile(filename, "UTF-8")) { source =>
      source.getLines().flatMap(line => parser(line)).toSeq
    }


  /** Load the rates from a file. */
  @inline
  def rates(filename: String): Try[Seq[Rate]] =
    dataReader(filename, textToRate)


  /** Load cabin prices from a file */
  @inline
  def cabinPrices(filename: String): Try[Seq[CabinPrice]] =
    dataReader(filename, textToCabinPrice)


  /**
   * Read a [[Rate]] from text
   *
   * @param text Text to extract the rate from
   * @return A [[Rate]] if it could be parsed from the line, or empty if not
   */
  private def textToRate(text: String): Option[Rate] = {
    val rate = """Rate\((.*?), (.*)\)""".r("rateCode", "rateGroup")
    rate.findFirstMatchIn(text).map { matcher =>
      Rate(matcher.group("rateCode"), matcher.group("rateGroup"))
    }
  }


  /** Read a [[CabinPrice]] from text. If the price does not parse it will be empty */
  private def textToCabinPrice(text: String): Option[CabinPrice] = {
    val cabinPrice =
      """CabinPrice\((.*?), (.*?), (.*?)\)"""
        .r("cabinCode", "rateCode", "price")

    cabinPrice.findFirstMatchIn(text).flatMap { matcher =>
      Try(BigDecimal(matcher.group("price"))).toOption.map { case price =>
        CabinPrice(
          matcher.group("cabinCode"),
          matcher.group("rateCode"),
          price
        )
      }
    }
  }

}
