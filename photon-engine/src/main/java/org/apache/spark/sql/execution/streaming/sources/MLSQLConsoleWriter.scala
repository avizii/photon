package org.apache.spark.sql.execution.streaming.sources

import com.avizii.backup.photon.util.Logging
import org.apache.spark.sql.catalyst.plans.logical.LocalRelation
import org.apache.spark.sql.connector.write.streaming.{StreamingDataWriterFactory, StreamingWrite}
import org.apache.spark.sql.connector.write.{PhysicalWriteInfo, WriterCommitMessage}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.util.CaseInsensitiveStringMap
import org.apache.spark.sql.{DFVisitor, Dataset, SparkSession}

/** Common methods used to create writes for the the console sink */
class MLSQLConsoleWriter(schema: StructType, options: CaseInsensitiveStringMap)
  extends StreamingWrite with Logging {

  // Number of rows to display, by default 20 rows
  protected val numRowsToShow = options.getInt("numRows", 20)

  // Truncate the displayed data if it is too long, by default it is true
  protected val isTruncated = options.getBoolean("truncate", true)

  assert(SparkSession.getActiveSession.isDefined)
  protected val spark = SparkSession.getActiveSession.get

  def createStreamingWriterFactory(info: PhysicalWriteInfo): StreamingDataWriterFactory =
    PackedRowWriterFactory

  override def commit(epochId: Long, messages: Array[WriterCommitMessage]): Unit = {
    // We have to print a "Batch" label for the epoch for compatibility with the pre-data source V2
    // behavior.
    printRows(messages, schema, s"Batch: $epochId")
  }

  def abort(epochId: Long, messages: Array[WriterCommitMessage]): Unit = {}

  protected def printRows(
                           commitMessages: Array[WriterCommitMessage],
                           schema: StructType,
                           printMessage: String): Unit = {
    val rows = commitMessages.collect {
      case PackedRowCommitMessage(rs) => rs
    }.flatten

    // scalastyle:off println
    logInfo(format("-------------------------------------------"))
    logInfo(format(printMessage))
    logInfo(format("-------------------------------------------"))
    // scalastyle:off println
    val newdata = Dataset.ofRows(spark, LocalRelation(schema.toAttributes, rows))
    val value = DFVisitor.showString(newdata, numRowsToShow, 20, isTruncated)
    value.split("\n").foreach { line =>
      logInfo(format(line))
    }
  }

  def format(str: String) = {
    val prefix = if (options.get("LogPrefix") != null) {
      options.get("LogPrefix")
    } else ""
    s"${prefix} ${str}"
  }

  override def toString(): String = {
    s"ConsoleWriter[numRows=$numRowsToShow, truncate=$isTruncated]"
  }
}
