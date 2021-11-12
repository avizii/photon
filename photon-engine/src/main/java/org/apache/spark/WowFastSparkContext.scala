package org.apache.spark

import org.apache.spark.util.WowCacheableClosureCleaner

class WowFastSparkContext(sparkConf: SparkConf) extends SparkContext(sparkConf) {
  override private[spark] def clean[F <: AnyRef](f: F, checkSerializable: Boolean = true): F = {
    WowCacheableClosureCleaner.clean(f, checkSerializable)
    f
  }
}
