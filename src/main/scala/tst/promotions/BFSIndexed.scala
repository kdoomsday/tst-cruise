package tst.promotions


import tst.models.Promotion

import tst.models.PromotionCombo
import scala.annotation.tailrec


object BFSIndexed extends PromotionsSolver {

  override def tag: String = "BFSIndexed"

  private case class Solution(codes: Set[Int], available: Set[Int])


  override def allCombinablePromotions(
      allPromotions: Seq[Promotion]
  ): Seq[PromotionCombo] = {
    @tailrec
    def solveFor(acc: Set[Solution], fixed: Set[Solution]): Set[Solution] = {
      val newSolutions = // All valid new solutions from previous solutions
        for {
          sol <- acc
          ind <- sol.available
        } yield sol.copy(sol.codes + ind, newAvailable(ind, sol.available))

      if (newSolutions.isEmpty) fixed ++ acc
      else {
        // Solutions that were not improved here will also not be improved further
        val notImproved =
          acc.filterNot(s => newSolutions.exists(newS => s.codes.subsetOf(newS.codes)))

        solveFor(newSolutions, fixed ++ notImproved)
      }
    }

    inline def newAvailable(ind: Int, available: Set[Int]): Set[Int] =
      (available - ind)
        .filterNot { i =>
          val promotion = allPromotions(ind)
          val p         = allPromotions(i)
          p.notCombinableWith.contains(promotion.code) || promotion
            .notCombinableWith
            .contains(p.code)
        }

    solveFor(Set(Solution(Set.empty, allPromotions.indices.toSet)), Set.empty).map {
      sol =>
        val codes = sol.codes.map(i => allPromotions(i).code).toSeq
        PromotionCombo(codes)
    }.toSeq
  }

}
