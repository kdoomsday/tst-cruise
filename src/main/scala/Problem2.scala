import tst.Readers
import tst.models.PromotionCombo
import tst.models.Promotion
import scala.annotation.tailrec


@main def problem2(): Unit = {
  val promotions = Readers.promotions("data/promotions.txt").get
  allCombinablePromotions(promotions).foreach(println)
  println("---")
  println("Combos for P1:")
  combinablePromotions("P1", promotions).foreach(println)
  println("---")
  println("Combos for P3:")
  combinablePromotions("P3", promotions).foreach(println)
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


def combinablePromotions(
    promotionCode: String,
    allPromotions: Seq[Promotion]
): Seq[PromotionCombo] =
  allCombinablePromotions(allPromotions).filter(_.promotionCodes.contains(promotionCode))
