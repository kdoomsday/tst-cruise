package tst.promotions


import tst.models.Promotion

import tst.models.PromotionCombo


/**
 * Finds all combinable promotions by looking at all possible combinations and
 * then ruling out invalid ones. After this is done, removes non-optimal solutions
 */
object CombinationsPromotionsSolver extends PromotionsSolver {

  override def allCombinablePromotions(
      allPromotions: Seq[Promotion]
  ): Seq[PromotionCombo] = {

    def isValidCombination(promotions: Seq[Promotion]): Boolean = {
      val codes      = promotions.map(_.code)
      val rejections = promotions.flatMap(_.notCombinableWith)
      codes.forall(code => !rejections.contains(code))
    }

    val allValidCombos =
      (1 to allPromotions.size)
        .flatMap(size => allPromotions.combinations(size))
        .collect {
          case promotions if isValidCombination(promotions) =>
            PromotionCombo(promotions.map(_.code))
        }
        .toSeq

    // Remove non-optimal solutions
    allValidCombos.filter { combo =>
      !allValidCombos.exists { other =>
        combo != other && combo.promotionCodes.forall(other.promotionCodes.contains)
      }
    }
  }

}
