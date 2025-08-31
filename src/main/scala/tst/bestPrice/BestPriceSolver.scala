package tst.bestPrice


import tst.models.BestGroupPrice
import tst.models.CabinPrice
import tst.models.Rate


trait BestPriceSolver {

  /**
    * Get best price for each rate group
    *
    * @param rates Available rates
    * @param cabinPrices Available cabin prices
    * @return Seq of [[BestGroupPrice]] with the best price per rate group
    */
  def getBestGroupPrices(
      rates: Seq[Rate],
      cabinPrices: Seq[CabinPrice]
  ): Seq[BestGroupPrice]

}
