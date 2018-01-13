package org.jobinterview.exchange

import scala.collection.JavaConverters._
import java.nio.file.{Files, Paths}

object AccountWriter {

  def write(fileName: String, accounts: Seq[Account]): Unit = {
    val lines = for (acc <- accounts) yield {
      acc.id + "\t" + acc.moneyBalance + "\t" +
        acc.assetsBalance.getOrElse("A", 0) + "\t" +
        acc.assetsBalance.getOrElse("B", 0) + "\t" +
        acc.assetsBalance.getOrElse("C", 0) + "\t" +
        acc.assetsBalance.getOrElse("D", 0)
    }

    Files.write(Paths.get(fileName), lines.asJava)
  }

}
