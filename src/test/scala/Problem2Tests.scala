import tst.models.Promotion
import tst.models.PromotionCombo
import tst.Readers


class Problem2Tests extends munit.FunSuite {
  val promotions = Readers.promotions("data/promotions.txt").get


  test("simple all combine") {
    val p1 = Promotion("P1", Seq("P2"))
    val p2 = Promotion("P2", Seq("P1"))

    val res = Problem2.allCombinablePromotions(Seq(p1, p2))
    assert(res.size == 2)
    assert(res.contains(PromotionCombo(Seq("P1"))))
    assert(res.contains(PromotionCombo(Seq("P2"))))
  }


  test("combine more than one level") {
    val p1 = Promotion("P1", Seq("P2"))
    val p2 = Promotion("P2", Seq("P1", "P3"))
    val p3 = Promotion("P3", Seq("P2"))

    val res = Problem2.allCombinablePromotions(Seq(p1, p2, p3))
    assert(res.size == 2)
    assert(res.contains(PromotionCombo(Seq("P2"))))
    assert(res.contains(PromotionCombo(Seq("P1", "P3"))))
  }


  test("problem input test") {
    val res = Problem2.allCombinablePromotions(promotions)

    val sortedRes = res.map(sortCombo)

    assertEquals(sortedRes.size, 4)
    assert(sortedRes.contains(PromotionCombo(Seq("P1", "P2"))))
    assert(sortedRes.contains(PromotionCombo(Seq("P1", "P4", "P5"))))
    assert(sortedRes.contains(PromotionCombo(Seq("P2", "P3"))))
    assert(sortedRes.contains(PromotionCombo(Seq("P3", "P4", "P5"))))
  }


  test("problem input promotion combinations for P1") {
    val res      = Problem2.combinablePromotions("P1", promotions).map(sortCombo)
    val expected = Seq(
      PromotionCombo(Seq("P1", "P2")),
      PromotionCombo(Seq("P1", "P4", "P5"))
    )
    assertEquals(res, expected)
  }


  test("problem input promotion combinations for P3") {
    val res      = Problem2.combinablePromotions("P3", promotions).map(sortCombo)
    val expected = Seq(
      PromotionCombo(Seq("P2", "P3")),
      PromotionCombo(Seq("P3", "P4", "P5"))
    )
    assertEquals(res, expected)
  }


  /**
   * The solution does not sort promotion codes within a combo (but it could)
   * We sort here to simplify comparisons. If the spec calls to sort the promotion codes this would become unnecessary.
   *
   * Our sort expects all codes to be one character followed by an Integer.
   *
   * @param combo [[PromotionCombo]]
   * @return [[PromotionCombo]] where all codes are sorted by the number
   */
  private inline def sortCombo(combo: PromotionCombo): PromotionCombo =
    combo.copy(promotionCodes = combo.promotionCodes.sortBy(_.drop(1).toInt))

}
