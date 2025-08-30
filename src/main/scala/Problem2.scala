import tst.Readers
import tst.models.PromotionCombo
import tst.models.Promotion
import scala.annotation.tailrec


object problem2 {

  def main(args: Array[String]): Unit = {
    val allPromotions = Readers.promotions("data/promotions.txt").get

    if (args.isEmpty) printAll(allPromotions)
    else printPromotions(args(0), allPromotions)
  }


  /** All optimal combinations of promotions that can be built */
  def allCombinablePromotions(allPromotions: Seq[Promotion]): Seq[PromotionCombo] = {

    def isValidCombination(promotions: Seq[Promotion]): Boolean = {
      val codes      = promotions.map(_.code)
      val rejections = promotions.flatMap(_.notCombinableWith)
      codes.forall(code => !rejections.contains(code))
    }

    val allCombos =
      (1 to allPromotions.size) // Combos must contain at least 1 promotion
        .iterator
        .flatMap(size => allPromotions.combinations(size))
        .collect {
          case promotions if isValidCombination(promotions) =>
            PromotionCombo(promotions.map(_.code))
        }
        .toSeq

    // Remove non-optimal solutions
    allCombos.filter { combo =>
      !allCombos.exists { other =>
        combo != other && combo.promotionCodes.forall(other.promotionCodes.contains)
      }
    }
  }


  /** All Promotion Combos for a given Promotion Code */
  def combinablePromotions(
      promotionCode: String,
      allPromotions: Seq[Promotion]
  ): Seq[PromotionCombo] =
    allCombinablePromotions(allPromotions)
      .filter(_.promotionCodes.contains(promotionCode))


  /** Print all promotions, as well as combinable for P1 and P3 */
  def printAll(allPromotions: Seq[Promotion]): Unit = {
    allCombinablePromotions(allPromotions).foreach(println)
    println("---")
    printPromotions("P1", allPromotions)
    println("---")
    printPromotions("P3", allPromotions)
  }


  /** print combinable promotions for a specific promotion code */
  def printPromotions(promotionCode: String, allPromotions: Seq[Promotion]): Unit = {
    println(s"Combos for $promotionCode:")
    combinablePromotions("P1", allPromotions)
      .foreach(combo => println(s"\t$combo"))
  }

}
