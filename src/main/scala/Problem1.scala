import tst.Readers
import tst.bestPrice.BestPriceSolver
import tst.bestPrice.BestPriceSolverImpl
import tst.models.BestGroupPrice
import tst.models.CabinPrice
import tst.models.Rate


object Problem1 {
  def main(args: Array[String]): Unit = {
    // For read errors in this case we want the exception
    val rates       = Readers.rates("data/rates.txt").get
    val cabinPrices = Readers.cabinPrices("data/cabin-prices.txt").get

    val solver: BestPriceSolver = BestPriceSolverImpl
    solver.getBestGroupPrices(rates, cabinPrices).foreach(println)
  }
}
