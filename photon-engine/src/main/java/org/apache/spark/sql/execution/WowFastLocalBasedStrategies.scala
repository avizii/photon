package org.apache.spark.sql.execution

import org.apache.spark.sql.SparkSession

object WowFastLocalBasedStrategies {
  def register(sparkSession: SparkSession): Unit = {
    sparkSession.experimental.extraStrategies = Seq(
      new WowFastLocalTableScanStrategies()
    ) ++: sparkSession.experimental.extraStrategies

    sparkSession.sessionState.optimizer.batches
  }

  def unRegister(sparkSession: SparkSession): Unit = {
    sparkSession.experimental.extraStrategies =
      sparkSession.experimental.extraStrategies
       .filter(strategy => !strategy.isInstanceOf[WowFastLocalTableScanStrategies])
  }
}
