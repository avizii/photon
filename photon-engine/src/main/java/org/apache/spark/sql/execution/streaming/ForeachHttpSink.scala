package org.apache.spark.sql.execution.streaming

import org.apache.spark.internal.Logging
import org.apache.spark.sql._

class ForeachHttpSink(writer: RestResponse) extends Sink with Logging {

  override def addBatch(batchId: Long, data: DataFrame): Unit = synchronized {
    val result = data.sparkSession.createDataFrame(
      data.sparkSession.sparkContext.parallelize(data.collect()), data.schema).toJSON.collect().mkString("")
    writer.write(result)
  }
}
