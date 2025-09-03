package tst.promotions


import tst.models.Promotion
import tst.models.PromotionCombo


trait PromotionsSolver {
  def tag: String

  /**
   * Find all [[PromotionCombo]] with maximum number of combinable promotions
   * This means getting the optimum combinations. Given:
   * <ul>
   * <li>`Combo1` with Promotion 1</li>
   * <li>`Combo2` with Promotions 1 and 2</li>
   * </ul>
   *
   * The result should only contain `Combo2`, as it includes all the promotions
   * that are in `Combo1`, plus more
   *
   * @param allPromotions All available [[Promotion]] instances
   * @return All Promotion Combos with maximum combinable promotions
   */
  def allCombinablePromotions(allPromotions: Seq[Promotion]): Seq[PromotionCombo]


  /**
   * Find all [[PromotionCombo]] for a given promotion
   *
   * @param promotionCode Code of the [[Promotion]] to look for
   * @param allPromotions All available Promotions
   * @return All PromotionCombos that include the specified promotion
   */
  def combinablePromotions(
      promotionCode: String,
      allPromotions: Seq[Promotion]
  ): Seq[PromotionCombo] =
    allCombinablePromotions(allPromotions)
      .filter(_.promotionCodes.contains(promotionCode))

}
