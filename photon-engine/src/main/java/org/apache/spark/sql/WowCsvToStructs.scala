package org.apache.spark.sql

import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.catalyst.csv.{CSVOptions, UnivocityParser}
import org.apache.spark.sql.catalyst.expressions.codegen.CodegenFallback
import org.apache.spark.sql.catalyst.expressions.{ExpectsInputTypes, Expression, TimeZoneAwareExpression, UnaryExpression}
import org.apache.spark.sql.catalyst.util.{BadRecordException, GenericArrayData}
import org.apache.spark.sql.types._

case class WowCsvToStructs(schema: DataType,
                           options: Map[String, String],
                           child: Expression,
                           timeZoneId: Option[String],
                           forceNullableSchema: Boolean) extends UnaryExpression with TimeZoneAwareExpression with CodegenFallback with ExpectsInputTypes {
  val nullableSchema = schema.asNullable

  def this(schema: DataType, options: Map[String, String], child: Expression) =
    this(schema, options, child, timeZoneId = None,
      forceNullableSchema = true)


  // This converts parsed rows to the desired output by the given schema.
  @transient
  lazy val converter = nullableSchema match {
    case _: StructType =>
      (rows: Seq[InternalRow]) => if (rows.length == 1) rows.head else null
    case ArrayType(_: StructType, _) =>
      (rows: Seq[InternalRow]) => new GenericArrayData(rows)
  }

  @transient
  lazy val parser =
    new UnivocityParser(
      rowSchema,
      new CSVOptions(options, true, timeZoneId.get))

  @transient
  lazy val rowSchema = nullableSchema match {
    case st: StructType => st
    case ArrayType(st: StructType, _) => st
  }

  override def withTimeZone(timeZoneId: String): TimeZoneAwareExpression =
    copy(timeZoneId = Option(timeZoneId))

  override def inputTypes: Seq[AbstractDataType] = StringType :: Nil

  override def dataType: DataType = nullableSchema

  override def nullSafeEval(json: Any): Any = {
    // When input is,
    //   - `null`: `null`.
    //   - invalid json: `null`.
    //   - empty string: `null`.
    //
    // When the schema is array,
    //   - json array: `Array(Row(...), ...)`
    //   - json object: `Array(Row(...))`
    //   - empty json array: `Array()`.
    //   - empty json object: `Array(Row(null))`.
    //
    // When the schema is a struct,
    //   - json object/array with single element: `Row(...)`
    //   - json array with multiple elements: `null`
    //   - empty json array: `null`.
    //   - empty json object: `Row(null)`.

    // We need `null` if the input string is an empty string. `JacksonParser` can
    // deal with this but produces `Nil`.
    if (json.toString.trim.isEmpty) return null

    try {
      converter(Seq(parser.parse(json.asInstanceOf[org.apache.spark.unsafe.types.UTF8String].toString).get))
    } catch {
      case _: BadRecordException => null
    }
  }


}
