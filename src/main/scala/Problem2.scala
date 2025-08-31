import tst.Readers
import tst.models.PromotionCombo
import tst.models.Promotion
import scala.annotation.tailrec


object Problem2 {

  def main(args: Array[String]): Unit = {
    val allPromotions = Readers.promotions("data/promotions.txt").get

    if (args.isEmpty) printAll(allPromotions)
    else printPromotions(args(0), allPromotions)
  }



  /**
    * Model a solution in progress
    *
    * @param codes All codes that are part of this solution
    * @param rejections All codes that cannot be accepted in the solution
    * @param available Promotions that have not yet been processed for this solution
    */
  case class Solution(
      codes: Set[String],
      rejections: Set[String],
      available: Set[Promotion]
  )


  def allCombinablePromotions(allPromotions: Seq[Promotion]): Seq[PromotionCombo] = {
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
        solveFor(newSolutions, fixed ++ newFixed) // Only keep generating for new solutions
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
