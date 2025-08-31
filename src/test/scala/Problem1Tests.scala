import tst.Readers
import tst.models.BestGroupPrice
import tst.models.Rate
import tst.models.CabinPrice

class Problem1Tests extends munit.FunSuite {
  val rates = Readers.rates("data/rates.txt").get
  val cabinPrices = Readers.cabinPrices("data/cabin-prices.txt").get

  test("simple best prices") {
    val rates = Seq(Rate("M1", "Military"))
    val prices = Seq(CabinPrice("CA", "M1", BigDecimal("200.00")))

    val res = Problem1.bestPrices(rates, prices)
    assertEquals(res.size, 1)
    assertEquals(res.head, BestGroupPrice("CA", "M1", BigDecimal("200.00"), "Military"))
  }

  test("no best price found") {
    val rates = Seq(Rate("M1", "Military"))
    val prices = Seq(CabinPrice("CA", "S1", BigDecimal("225.00")))

    val res = Problem1.bestPrices(rates, prices)
    assert(res.isEmpty)
  }

  test("problem 1 input test") {
    val res = Problem1.bestPrices(rates, cabinPrices)

    assertEquals(res.size, 4)
    assert(res.contains(BestGroupPrice("CA", "M1", BigDecimal("200.00"), "Military")))
    assert(res.contains(BestGroupPrice("CA", "S1", BigDecimal("225.00"), "Senior")))
    assert(res.contains(BestGroupPrice("CB", "M1", BigDecimal("230.00"), "Military")))
    assert(res.contains(BestGroupPrice("CB", "S1", BigDecimal("245.00"), "Senior")))
  }
}
