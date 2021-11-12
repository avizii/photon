package org.apache.spark.sql.catalyst.expressions

import com.avizii.backup.photon.util.Logging
import org.apache.spark.sql.types.DataType

import scala.util.Properties

case class WowScalaUDF(function: AnyRef,
                       dataType: DataType,
                       children: Seq[Expression],
                       inputsNullSafe: Seq[Boolean],
                       inputTypes: Seq[DataType] = Nil,
                       udfName: Option[String] = None,
                       nullable: Boolean = true,
                       udfDeterministic: Boolean = true) {

  def this(
           function: AnyRef,
           dataType: DataType,
           children: Seq[Expression]
          ) = {
    this(
      function, dataType, children, WowScalaUDF.getParameterTypeNullability(function),
      Nil, None, nullable = true, udfDeterministic = true)
  }


  def toScalaUDF = {

    new ScalaUDF(function,
      dataType,
      children,
      Nil,
      None,
      udfName,
      nullable,
      udfDeterministic
    )
  }

}

object WowScalaUDF extends Logging {
  def getParameterTypeNullability(func: AnyRef): Seq[Boolean] = {
    if (!Properties.versionString.contains("2.11")) {
      logWarning(s"Scala ${Properties.versionString} cannot get type nullability correctly via " +
       "reflection, thus Spark cannot add proper input null check for UDF. To avoid this " +
       "problem, use the typed UDF interfaces instead.")
    }
    val methods = func.getClass.getMethods.filter(m => m.getName == "apply" && !m.isBridge)
    assert(methods.length == 1)
    methods.head.getParameterTypes.map(!_.isPrimitive)
  }
}

