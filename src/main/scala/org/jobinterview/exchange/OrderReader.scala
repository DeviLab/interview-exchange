package org.jobinterview.exchange

object OrderReader extends Reader[Order] {

  protected def parse(parts: Array[String]): Order = {
    val accountId = parts(0)
    val operation = parts(1) match {
      case "b" => Buy
      case "s" => Sell
    }
    val asset = parts(2)
    val price = parts(3).toInt
    val amount = parts(4).toInt

    Order(accountId, operation, asset, amount, price)
  }

}
