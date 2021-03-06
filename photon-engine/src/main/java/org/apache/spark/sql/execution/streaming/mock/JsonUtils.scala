package org.apache.spark.sql.execution.streaming.mock

import org.json4s.NoTypeHints
import org.json4s.jackson.Serialization

import scala.collection.mutable.HashMap
import scala.util.control.NonFatal

/**
 * Utilities for converting Kafka related objects to and from json.
 */
private object JsonUtils {
  private implicit val formats = Serialization.formats(NoTypeHints)

  /**
   * Read TopicPartitions from json string
   */
  def partitions(str: String): Array[TopicPartition] = {
    try {
      Serialization.read[Map[String, Seq[Int]]](str).flatMap { case (topic, parts) =>
        parts.map { part =>
          new TopicPartition(topic, part)
        }
      }.toArray
    } catch {
      case NonFatal(x) =>
        throw new IllegalArgumentException(
          s"""Expected e.g. {"topicA":[0,1],"topicB":[0,1]}, got $str""")
    }
  }

  /**
   * Write TopicPartitions as json string
   */
  def partitions(partitions: Iterable[TopicPartition]): String = {
    val result = new HashMap[String, List[Int]]
    partitions.foreach { tp =>
      val parts: List[Int] = result.getOrElse(tp.topic, Nil)
      result += tp.topic -> (tp.partition :: parts)
    }
    Serialization.write(result)
  }

  /**
   * Read per-TopicPartition offsets from json string
   */
  def partitionOffsets(str: String): Map[TopicPartition, Long] = {
    try {
      Serialization.read[Map[String, Map[Int, Long]]](str).flatMap { case (topic, partOffsets) =>
        partOffsets.map { case (part, offset) =>
          new TopicPartition(topic, part) -> offset
        }
      }.toMap
    } catch {
      case NonFatal(x) =>
        throw new IllegalArgumentException(
          s"""Expected e.g. {"topicA":{"0":23,"1":-1},"topicB":{"0":-2}}, got $str""")
    }
  }

  /**
   * Write per-TopicPartition offsets as json string
   */
  def partitionOffsets(partitionOffsets: Map[TopicPartition, Long]): String = {
    val result = new HashMap[String, HashMap[Int, Long]]()
    implicit val ordering = new Ordering[TopicPartition] {
      override def compare(x: TopicPartition, y: TopicPartition): Int = {
        Ordering.Tuple2[String, Int].compare((x.topic, x.partition), (y.topic, y.partition))
      }
    }
    val partitions = partitionOffsets.keySet.toSeq.sorted // sort for more determinism
    partitions.foreach { tp =>
      val off = partitionOffsets(tp)
      val parts = result.getOrElse(tp.topic, new HashMap[Int, Long])
      parts += tp.partition -> off
      result += tp.topic -> parts
    }
    Serialization.write(result)
  }
}
