package org.apache.spark.sql.execution

import org.apache.spark.sql.SparkSession

import java.util.concurrent.atomic.AtomicReference
import scala.collection.mutable.ArrayBuffer

object MLSQLAuthParser {
  val parser = new AtomicReference[WowSparkSqlParser]()

  def filterTables(sql: String, session: SparkSession) = {
    val t = ArrayBuffer[WowTableIdentifier]()
    lazy val parserInstance = new WowSparkSqlParser(session.sqlContext.conf)
    parser.compareAndSet(null, parserInstance)
    parser.get().tables(sql, t)
    t
  }
}
