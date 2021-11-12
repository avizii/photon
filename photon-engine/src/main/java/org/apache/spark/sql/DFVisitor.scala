package org.apache.spark.sql

object DFVisitor {
  def showString(df: DataFrame, _numRows: Int,
                 truncate: Int = 20,
                 vertical: Boolean = false) = df.showString(_numRows, truncate, vertical)

}
