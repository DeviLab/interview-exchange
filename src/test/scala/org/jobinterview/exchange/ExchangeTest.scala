package org.jobinterview.exchange

import org.scalatest.{FlatSpec, Matchers}

class ExchangeTest extends FlatSpec with Matchers {

  "matching" should "work in simple cases" in {
    val accounts = Seq(
      Account("a", 5, Map("A" -> 2, "B" -> 3)),
      Account("b", 2, Map("A" -> 9, "B" -> 12))
    )

    val orders = Seq(
      Order("a", Buy, "B", amount = 3, price = 5),
      Order("b", Sell, "B", amount = 3, price = 5),
      Order("a", Buy, "C", amount = 12, price = 13),
      Order("a", Buy, "D", amount = 1, price = 2),
      Order("b", Sell, "D", amount = 2, price = 1),
      Order("a", Buy, "E", amount = 3, price = 1),
      Order("b", Sell, "E", amount = 3, price = 1)
    )

    Exchange.matchOrders(orders, accounts) shouldEqual Seq(
      Account("a", -13, Map("A" -> 2, "B" -> 6, "E" -> 3)),
      Account("b", 20, Map("A" -> 9, "B" -> 9, "E" -> -3))
    )
  }

  "unknown accounts" should "be ignored" in {
    val accounts = Seq(
      Account("a", 0, Map()),
      Account("b", 0, Map())
    )

    val orders = Seq(
      Order("a", Buy, "A", amount = 3, price = 0),
      Order("b", Sell, "A", amount = 3, price = 0),
      Order("a", Buy, "B", amount = 3, price = 0),
      Order("bb", Sell, "B", amount = 3, price = 0)
    )

    Exchange.matchOrders(orders, accounts) shouldEqual Seq(
      Account("a", 0, Map("A" -> 3)),
      Account("b", 0, Map("A" -> -3))
    )
  }

  "first orders" should "have higher priority" in {
    val accounts = Seq(
      Account("a", 0, Map()),
      Account("b", 0, Map()),
      Account("c", 0, Map()),
      Account("d", 0, Map()),
      Account("e", 0, Map())
    )

    val orders = Seq(
      Order("a", Buy, "B", amount = 3, price = 0),
      Order("b", Buy, "B", amount = 3, price = 0),
      Order("c", Buy, "B", amount = 3, price = 0),
      Order("d", Sell, "B", amount = 3, price = 0),
      Order("e", Sell, "B", amount = 3, price = 0)
    )

    Exchange.matchOrders(orders, accounts) shouldEqual Seq(
      Account("a", 0, Map("B" -> 3)),
      Account("b", 0, Map("B" -> 3)),
      Account("c", 0, Map()),
      Account("d", 0, Map("B" -> -3)),
      Account("e", 0, Map("B" -> -3))
    )
  }

}
