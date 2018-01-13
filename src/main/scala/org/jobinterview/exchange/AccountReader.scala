package org.jobinterview.exchange

object AccountReader extends Reader[Account] {

  protected def parse(parts: Array[String]): Account = {
    val accountId = parts(0)
    val moneyBalance = parts(1).toInt
    val balanceA = parts(2).toInt
    val balanceB = parts(3).toInt
    val balanceC = parts(4).toInt
    val balanceD = parts(5).toInt

    Account(accountId, moneyBalance, Map("A" -> balanceA, "B" -> balanceB, "C" -> balanceC, "D" -> balanceD))
  }

}