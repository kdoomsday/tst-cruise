import tst.Readers
import tst.promotions.BFSPromotionsSolver
import scala.util.Success
import scala.util.Failure


/**
 * Runner that loads a specified promotions file and prints all combinable
 * promotions.
 */
object PromotionsLoad {

  def main(args: Array[String]): Unit = {
    val promotionsFilename = args.headOption.getOrElse("data/promotions.txt")
    val promotions         = Readers.promotions(promotionsFilename)

    promotions match {
      case Success(allPromotions) =>
        val solver = BFSPromotionsSolver
        val combos = solver.allCombinablePromotions(allPromotions)
        println("All combinable promotions")
        combos.foreach(c => println(s"\t$c"))

      case Failure(exception) =>
        println(s"Error loading file '$promotionsFilename' (${exception.getMessage()})")
        exception.printStackTrace()
    }
  }

}
