import tst.Readers
import tst.models.PromotionCombo
import tst.models.Promotion
import scala.annotation.tailrec


@main def problem2(): Unit = {
  val promotions = Readers.promotions("data/promotions.txt").get
  allCombinablePromotions(promotions).foreach(println)
}


/** All optimal combinations of promotions that can be built */
def allCombinablePromotions(allPromotions: Seq[Promotion]): Seq[PromotionCombo] = {

  def isValidCombination(promotions: Seq[Promotion]): Boolean = {
    val codes = promotions.map(_.code)
    val rejections = promotions.flatMap(_.notCombinableWith)
    codes.forall(code => !rejections.contains(code))
  }


  val allCombos =
    (1 to allPromotions.size)
      .flatMap(size => allPromotions.combinations(size))
      .collect { case promotions if isValidCombination(promotions) =>
        PromotionCombo(promotions.map(_.code))
      }.toSeq

  // Remove non-optimal solutions
  allCombos.filter { combo =>
    !allCombos.exists { other =>
      combo != other && combo.promotionCodes.forall(other.promotionCodes.contains)
    }
  }
}




