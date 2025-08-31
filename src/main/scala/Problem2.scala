import tst.Readers
import tst.models.PromotionCombo
import tst.models.Promotion
import tst.promotions.PromotionsSolver
import tst.promotions.BFSPromotionsSolver
import scala.annotation.tailrec


object Problem2 {

  def main(args: Array[String]): Unit = {
    val allPromotions = Readers.promotions("data/promotions.txt").get

    val solver = BFSPromotionsSolver
    if (args.isEmpty) printAll(allPromotions, solver)
    else printPromotions(args(0), allPromotions, solver)
  }


  /** Print all promotions, as well as combinable for P1 and P3 */
  def printAll(allPromotions: Seq[Promotion], solver: PromotionsSolver): Unit = {
    printCombos("All promotion combos", solver.allCombinablePromotions(allPromotions))

    println("\n---\n")

    printPromotions("P1", allPromotions, solver)

    println("\n---\n")

    printPromotions("P3", allPromotions, solver)
  }


  /** print combinable promotions for a specific promotion code */
  def printPromotions(
      promotionCode: String,
      allPromotions: Seq[Promotion],
      solver: PromotionsSolver
  ): Unit =
    printCombos(
      s"Combos for $promotionCode:",
      solver.combinablePromotions(promotionCode, allPromotions)
    )


  private def printCombos(title: String, combos: Seq[PromotionCombo]): Unit = {
    println(title)
    combos.foreach(combo => println(s"\t$combo"))
  }

}
