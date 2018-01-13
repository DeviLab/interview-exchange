package org.jobinterview.exchange

object Main extends App {

  val orders = OrderReader.read("orders.txt")
  val accounts = AccountReader.read("clients.txt")
  val matchingResult = Exchange.matchOrders(orders, accounts)
  AccountWriter.write("result.txt", matchingResult)

}
