package org.jobinterview.exchange

import scala.collection.JavaConverters._
import java.nio.file.{Files, Paths}

abstract class Reader[T] {

  def read(fileName: String): Seq[T] = {
    val lines = Files.lines(Paths.get(fileName)).iterator().asScala.toStream

    for (line <- lines) yield {
      parse(line.split("\t"))
    }
  }

  protected def parse(parts: Array[String]): T

}
