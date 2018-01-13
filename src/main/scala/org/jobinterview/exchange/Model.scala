package org.jobinterview.exchange

case class Order(accountId: String, direction: Direction, asset: String, amount: Int, price: Int) {

  val totalPrice: Int = price * amount

}

case class Account(id: String, moneyBalance: Int, assetsBalance: Map[String, Int]) {

  def processOrder(order: Order): Account = {
    val currentAssetBalance = assetsBalance.getOrElse(order.asset, 0)

    order.direction match {
      case Buy =>
        Account(id, moneyBalance + order.totalPrice, assetsBalance + (order.asset -> (currentAssetBalance - order.amount)))
      case Sell =>
        Account(id, moneyBalance - order.totalPrice, assetsBalance + (order.asset -> (currentAssetBalance + order.amount)))
    }
  }

}

sealed trait Direction
object Buy extends Direction
object Sell extends Direction
