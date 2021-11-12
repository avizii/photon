package org.apache.spark.udf

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.FunctionIdentifier
import org.apache.spark.sql.catalyst.expressions.{Expression, ScalaUDF}

object UDFManager {
  def register(sparkSession: SparkSession, name: String, udf: (Seq[Expression]) => ScalaUDF) = {
    sparkSession.sessionState.functionRegistry.registerFunction(FunctionIdentifier(name), udf)
  }
}
