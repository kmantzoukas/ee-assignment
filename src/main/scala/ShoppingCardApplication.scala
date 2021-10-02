import model.ShoppingCard
import model.Product

import scala.::

class ShoppingCardApplication(val card: ShoppingCard) {

  def addNProductToCard(product: Product, numOfProducts: Int): Unit = {
    1 to numOfProducts foreach (_ => card.products :+= product)
  }
}