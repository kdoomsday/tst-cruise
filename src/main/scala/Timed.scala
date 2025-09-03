import tst.Readers
import tst.promotions.PromotionsSolver
import tst.models.Promotion
import tst.promotions.CombinationsPromotionsSolver
import tst.promotions.BFSPromotionsSolver
import tst.promotions.BFSIndexed

object Timed {
  def main(args: Array[String]): Unit = {
    given promotions: Seq[Promotion] = Readers.promotions("data/promotions-extended.txt").get

    val (_, combs) = time(CombinationsPromotionsSolver.allCombinablePromotions(promotions))
    val (_, bfs) = time(BFSPromotionsSolver.allCombinablePromotions(promotions))
    val (_, indexed) = time(BFSIndexed.allCombinablePromotions(promotions))

    println(s"|$combs|$bfs|$indexed|")
  }

  private def time[T](block: => T): (res: T, time: Long) = {
    val start = System.nanoTime()
    val res = block
    val end = System.nanoTime()
    (res, end - start)
  }


  private def solve(solver: PromotionsSolver)(using promotions: Seq[Promotion]): Unit = {
    val (res, ellapsed) = time(solver.allCombinablePromotions(promotions))
    println(s"${solver.tag} size=${res.size}. Took $ellapsed ns")
  }
}
