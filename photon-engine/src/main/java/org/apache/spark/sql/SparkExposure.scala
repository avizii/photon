package org.apache.spark.sql

import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan

object SparkExposure {
  def cleanCache(spark: SparkSession, plan: LogicalPlan) = {
    spark.sqlContext.sharedState.cacheManager.uncacheQuery(spark, plan, false, true)
  }
}
