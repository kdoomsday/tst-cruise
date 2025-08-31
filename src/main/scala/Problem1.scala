import tst.Readers
import tst.models.Rate
import tst.models.CabinPrice
import tst.models.BestGroupPrice


object Problem1 {
  def main(args: Array[String]): Unit = {
    // For read errors in this case we want the exception
    val rates       = Readers.rates("data/rates.txt").get
    val cabinPrices = Readers.cabinPrices("data/cabin-prices.txt").get

    getBestGroupPrices(rates, cabinPrices).foreach(println)
  }

  /** Best Cabin Prices given the data */
  def getBestGroupPrices(rates: Seq[Rate], cabinPrices: Seq[CabinPrice]): Seq[BestGroupPrice] = {
    val matchingRates: Seq[(Rate, CabinPrice)] = for {
      rate       <- rates
      cabinPrice <- cabinPrices
      if (rate.rateCode == cabinPrice.rateCode)
    } yield (rate, cabinPrice)

    matchingRates
      .map { case (rate, price) =>
        BestGroupPrice(price.cabinCode, rate.rateCode, price.price, rate.rateGroup)
      }
      .groupBy(bgp => (bgp.cabinCode, bgp.rateGroup))
      .flatMap { case (_, prices) => prices.minByOption(_.price) }
      .toSeq
      .sortBy(bgp => (bgp.cabinCode, bgp.rateGroup))
  }

}
