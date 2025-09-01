import tst.Readers
import tst.bestPrice.BestPriceSolver
import tst.bestPrice.BestPriceSolverImpl
import tst.models.BestGroupPrice
import tst.models.CabinPrice
import tst.models.Rate


object Problem1 {

  def main(args: Array[String]): Unit = {
    // For read errors in this case we want the exception
    val (ratesFile, cabinPricesFile) = loadFiles(args)

    val rates       = Readers.rates(ratesFile).get
    val cabinPrices = Readers.cabinPrices(cabinPricesFile).get

    val solver: BestPriceSolver = BestPriceSolverImpl
    solver.getBestGroupPrices(rates, cabinPrices).foreach(println)
  }


  /**
   * Choose files to load given program arguments.
   * Usage `runMain Problem1 [<ratesFile>] [<cabinPricesFile>]`
   *
   * If the rates file is not specified, defaults to "data/rates.txt"
   * If the cabin prices file is not specified, defaults to "data/cabin-prices.txt"
   *
   * @param args Program arguments
   * @return Filename for rates and filename for cabin prices
   */
  private def loadFiles(args: Array[String]): (String, String) = {
    val ratesFile       = args.headOption.getOrElse("data/rates.txt")
    val cabinPricesFile = args.drop(1).headOption.getOrElse("data/cabin-prices.txt")
    (ratesFile, cabinPricesFile)
  }

}
