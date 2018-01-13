package org.jobinterview.exchange

object Exchange {

  def matchOrders(orders: Seq[Order], accounts: Seq[Account]): Seq[Account] = {
    val accountsById = accounts.groupBy(_.id).mapValues(_.head)
    val filteredOrders = orders.filter(o => accountsById.contains(o.accountId))

    val matchedOrders = filteredOrders
      .groupBy(o => (o.asset, o.amount, o.price))
      .mapValues { similarOrders =>
        val buyOrders = similarOrders.filter(_.direction == Buy)
        val sellOrders = similarOrders.filter(_.direction == Sell)

        buyOrders.zip(sellOrders)
      }
      .values.flatten

    val processedAccountsMap = matchedOrders.foldLeft(accountsById) {
      case (currentAccountsMap, (leftOrder, rightOrder)) =>
        val leftAccount = currentAccountsMap(leftOrder.accountId)
        val rightAccount = currentAccountsMap(rightOrder.accountId)

        currentAccountsMap +
          (leftAccount.id -> leftAccount.processOrder(rightOrder)) +
          (rightAccount.id -> rightAccount.processOrder(leftOrder))
    }

    accounts.map(a => processedAccountsMap(a.id))
  }

}
