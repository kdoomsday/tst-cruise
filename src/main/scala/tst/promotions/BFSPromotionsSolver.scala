package tst.promotions


import tst.models.Promotion

import tst.models.PromotionCombo
import scala.annotation.tailrec


object BFSPromotionsSolver extends PromotionsSolver {

  /**
   * Model a solution in progress
   *
   * @param codes All codes that are part of this solution
   * @param available Promotions that have not yet been processed for this
   * solution but are legal to test (can be combined still)
   */
  private case class Solution(codes: Set[String], available: Set[Promotion])


  override def allCombinablePromotions(
      allPromotions: Seq[Promotion]
  ): Seq[PromotionCombo] = {

    /**
     * Generate new solutions from existing solutions
     * All solutions that do not generate something new become fixed as they can
     * no longer be improved
     *
     * @param acc Current solution space
     * @param fixed Solutions found that can no longer improve
     * @return All optimal solutions
     */
    @tailrec
    def solveFor(acc: Set[Solution], fixed: Set[Solution]): Set[Solution] = {
      val newSolutions = // All valid new solutions from previous solutions
        for {
          sol <- acc
          p   <- sol.available
        } yield sol.copy(sol.codes + p.code, newAvailable(p, sol.available))

      if (newSolutions.isEmpty) fixed ++ acc
      else {
        // Solutions that were not improved here will also not be improved further
        val newFixed =
          acc.filterNot(s => newSolutions.exists(newS => s.codes.subsetOf(newS.codes)))

        solveFor(newSolutions, fixed ++ newFixed)
      }
    }

    val promotionsSet = allPromotions.toSet

    // Start with solutions for each promotion on its own
    val singleSolutions =
      allPromotions
        .map(p => Solution(Set(p.code), newAvailable(p, promotionsSet)))
        .toSet

    solveFor(singleSolutions, Set.empty)
      .map(sol => PromotionCombo(sol.codes.toSeq))
      .toSeq
  }


  /**
   * Available promotions if we use `promotion` in a solution, given a current
   * list of available promotion.
   *
   * We must remove 'promotion' itself as it is being used, all things it can't
   * combine, and all things that can't combine with it
   */
  private inline def newAvailable(
      promotion: Promotion,
      available: Set[Promotion]
  ): Set[Promotion] =
    (available - promotion)
      .filterNot { p =>
        p.notCombinableWith.contains(promotion.code) || promotion.notCombinableWith.contains(p.code)
      }

}
