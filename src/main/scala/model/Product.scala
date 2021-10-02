package model

import scala.language.implicitConversions
import scala.math.BigDecimal.RoundingMode

case class Product(name: String, price: BigDecimal)

object Product {
  def apply(name: String, price: BigDecimal): Product = new Product(name, price.setScale(2, RoundingMode.HALF_EVEN))
}

