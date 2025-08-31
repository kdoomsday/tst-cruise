package tst.bestPrice

import tst.models.BestGroupPrice
import tst.models.CabinPrice
import tst.models.Rate


object BestPriceSolverImpl extends BestPriceSolver {

  override def getBestGroupPrices(rates: Seq[Rate], cabinPrices: Seq[CabinPrice]): Seq[BestGroupPrice] = {
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
