import model.ShoppingCard
import model.Product
import org.scalatest.GivenWhenThen
import org.scalatest.funspec.AnyFunSpec

import scala.math.BigDecimal.RoundingMode

class ShoppingCardApplicationTest extends AnyFunSpec with GivenWhenThen{

 describe("A shopping card application") {

   it("should allow the addition of new products in the shopping card"){

    val shoppingCardApplication = new ShoppingCardApplication(ShoppingCard.apply())

    Given("An empty shopping card and a product, Dove Soap with a unit price of 39.99")
    val product = Product("Dove Soap", 39.99)

    When("The user adds 5 Dove Soaps to the shopping cart")
    shoppingCardApplication.addProductsToCard(product, 5)

    When("The user adds 3 Dove Soaps to the shopping cart")
    shoppingCardApplication.addProductsToCard(product, 3)

    Then("The shopping cart should contain 8 Dove Soaps each with a unit price of 39.99")
    assert(shoppingCardApplication.card.products.size == 8)
    shoppingCardApplication.card.products.foreach(p => assert(p.price == 39.99))

    And("The shopping cart’s total price should equal 319.92")
    assert(computeTotal(shoppingCardApplication.card.products) == BigDecimal(319.92))
  }

    def computeTotal(products: Seq[Product]): BigDecimal =
    products.foldLeft(BigDecimal(0.00))((c,p) => c + p.price).setScale(2, RoundingMode.HALF_EVEN)
 }
}
