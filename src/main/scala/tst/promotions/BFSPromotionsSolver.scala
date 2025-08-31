package tst.promotions


import tst.models.Promotion

import tst.models.PromotionCombo
import scala.annotation.tailrec


object BFSPromotionsSolver extends PromotionsSolver {

  /**
   * Model a solution in progress
   *
   * @param codes All codes that are part of this solution
   * @param rejections All codes that cannot be accepted in the solution
   * @param available Promotions that have not yet been processed for this solution
   */
  private case class Solution(
      codes: Set[String],
      rejections: Set[String],
      available: Set[Promotion]
  )


  override def allCombinablePromotions(
      allPromotions: Seq[Promotion]
  ): Seq[PromotionCombo] = {
    // Generate new solutions from existing solutions
    // All solutions that do not generate something new become fixed
    @tailrec
    def solveFor(acc: Set[Solution], fixed: Set[Solution]): Set[Solution] = {
      val newSolutions = // All valid new solutions from previous solutions
        for {
          sol <- acc
          p   <- sol.available
          if !sol.rejections.contains(p.code) &&
            !sol.codes.exists(c => p.notCombinableWith.contains(c))
        } yield sol.copy(
          sol.codes + p.code,
          sol.rejections ++ p.notCombinableWith,
          sol.available - p
        )

      if (newSolutions.isEmpty) fixed ++ acc
      else {
        val newFixed =
          acc.filterNot(s => newSolutions.exists(newS => s.codes.subsetOf(newS.codes)))
        solveFor(
          newSolutions,
          fixed ++ newFixed
        ) // Only keep generating for new solutions
      }
    }

    val promotionsSet = allPromotions.toSet

    val singleSolutions =
      allPromotions
        .map(p => Solution(Set(p.code), p.notCombinableWith.toSet, promotionsSet - p))
        .toSet

    solveFor(singleSolutions, Set.empty)
      .map(sol => PromotionCombo(sol.codes.toSeq))
      .toSeq
  }

}
