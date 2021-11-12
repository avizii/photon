package org.apache.spark

import org.apache.spark.sql.catalyst.encoders.RowEncoder
import org.apache.spark.sql.types.StructType

object WowRowEncoder {
  def toRow(schema: StructType) = {
    RowEncoder.apply(schema).resolveAndBind().createDeserializer()

  }

  def fromRow(schema: StructType) = {
    RowEncoder.apply(schema).resolveAndBind().createSerializer()
  }
}
