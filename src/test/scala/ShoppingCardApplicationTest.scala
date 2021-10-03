import model.ShoppingCard
import model.Product
import org.scalatest.GivenWhenThen
import org.scalatest.funspec.AnyFunSpec

import scala.math.BigDecimal.RoundingMode

class ShoppingCardApplicationTest extends AnyFunSpec with GivenWhenThen{

 describe("A shopping card application") {

   it("should allow the addition of new products in the shopping card"){

    val shoppingCardApplication = new ShoppingCardApplication(ShoppingCard.apply())

    Given("An empty shopping card and a product Dove Soap with a unit price of 39.99 and a product Axe Deo with a unit price of 99.99")
    val doveSoap = Product("Dove Soap", 39.99)
    val axeDeo = Product("Axe Deo", 99.99)
    val taxRate = 0.125

    When("The user adds 2 Dove Soaps to the shopping cart")
    shoppingCardApplication.addProductsToCard(doveSoap, 2)

    When("The user adds 2 Dove Soaps to the shopping cart")
    shoppingCardApplication.addProductsToCard(axeDeo, 2)

    val listOfProductsForName = (name: String, products: List[Product]) =>
     products.groupBy(p => p.name).getOrElse(name, List.empty[Product])

    Then("The shopping cart should contain 2 Dove Soaps each with a unit price of 39.99")
     val doveSoapInCard = listOfProductsForName("Dove Soap", shoppingCardApplication.card.products)

    assert(doveSoapInCard.size == 2)
    doveSoapInCard.foreach(p => assert(p == doveSoap))

    Then("The shopping cart should contain 2 Axe Deo each with a unit price of 99.99")
    val axeDeoInCard = listOfProductsForName("Axe Deo", shoppingCardApplication.card.products)
    assert(axeDeoInCard.size == 2)
    axeDeoInCard.foreach(p => assert(p == axeDeo))

    And("The shopping cart’s total price (including taxes) should equal 314.96")
    assert(computeTotal(shoppingCardApplication.card.products, price => price * (1 + taxRate)) == BigDecimal(314.96))

    And("The shopping cart’s total tax amount should equal 35.00")
    assert(computeTotal(shoppingCardApplication.card.products, price => price * taxRate) == BigDecimal(35.00))

  }

   def computeTotal(products: Seq[Product], f: BigDecimal => BigDecimal): BigDecimal =
    products.foldLeft(BigDecimal(0))((c,p) => c + f(p.price)).setScale(2, RoundingMode.HALF_EVEN)
 }
}