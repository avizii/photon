package org.apache.spark.sql.catalyst.logical

import org.apache.spark.sql.catalyst.expressions.{Attribute, Literal}
import org.apache.spark.sql.catalyst.plans.logical.{LeafNode, LocalRelation, Statistics}
import org.apache.spark.sql.catalyst.{InternalRow, analysis}

case class WowFastLocalRelation(
                                output: Seq[Attribute],
                                data: Seq[InternalRow] = Nil
                               ) extends LeafNode with analysis.MultiInstanceRelation {

  // A local relation must have resolved output.
  require(output.forall(_.resolved), "Unresolved attributes found when constructing LocalRelation.")

  /**
   * Returns an identical copy of this relation with new exprIds for all attributes.  Different
   * attributes are required when a relation is going to be included multiple times in the same
   * query.
   */
  override final def newInstance(): this.type = {
    LocalRelation(output.map(_.newInstance()), data).asInstanceOf[this.type]
  }

  override protected def stringArgs: Iterator[Any] = {
    if (data.isEmpty) {
      Iterator("<empty>", output)
    } else {
      Iterator(output)
    }
  }

  override def computeStats(): Statistics =
    Statistics(sizeInBytes = output.map(n => BigInt(n.dataType.defaultSize)).sum * data.length)

  def toSQL(inlineTableName: String): String = {
    require(data.nonEmpty)
    val types = output.map(_.dataType)
    val rows = data.map { row =>
      val cells = row.toSeq(types).zip(types).map { case (v, tpe) => Literal(v, tpe).sql }
      cells.mkString("(", ", ", ")")
    }
    "VALUES " + rows.mkString(", ") +
     " AS " + inlineTableName +
     output.map(_.name).mkString("(", ", ", ")")
  }

}
