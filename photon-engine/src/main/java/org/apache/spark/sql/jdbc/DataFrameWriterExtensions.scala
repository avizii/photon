package org.apache.spark.sql.jdbc

import org.apache.spark.sql.execution.datasources.jdbc.{JDBCOptions, JdbcOptionsInWrite, JdbcUtils}
import org.apache.spark.sql.{DataFrame, DataFrameWriter, Row, SaveMode}

object DataFrameWriterExtensions {

  implicit class Upsert(w: DataFrameWriter[Row]) {
    def upsert(idCol: Option[String], jdbcOptions: JDBCOptions, df: DataFrame): Unit = {
      val idColumn = idCol.map(f => df.schema.filter(s => f.split(",").contains(s.name)))
      val url = jdbcOptions.url
      val table = jdbcOptions.tableOrQuery
      val modeF = w.getClass.getDeclaredField("mode")
      modeF.setAccessible(true)
      val mode = modeF.get(w).asInstanceOf[SaveMode]
      val conn = JdbcUtils.createConnectionFactory(jdbcOptions)()
      val isCaseSensitive = df.sqlContext.conf.caseSensitiveAnalysis
      val writeOption = new JdbcOptionsInWrite(url, table, jdbcOptions.parameters)
      try {

        var tableExists = JdbcUtils.tableExists(conn, writeOption)

        if (mode == SaveMode.Ignore && tableExists) {
          return
        }

        if (mode == SaveMode.ErrorIfExists && tableExists) {
          sys.error(s"Table $table already exists.")
        }

        if (mode == SaveMode.Overwrite && tableExists) {
          JdbcUtils.dropTable(conn, table, writeOption)
          tableExists = false
        }

        // Create the table if the table didn't exist.
        if (!tableExists) {
          val isCaseSensitive = df.sparkSession.sqlContext.conf.caseSensitiveAnalysis
          val schema = JdbcUtils.schemaString(df.schema, isCaseSensitive, url, jdbcOptions.createTableColumnTypes)
          val dialect = JdbcDialects.get(url)
          val pk = idColumn.map { f =>
            val key = f.map(c => s"${dialect.quoteIdentifier(c.name)}").mkString(",")
            s", primary key(${key})"
          }.getOrElse("")
          val sql = s"CREATE TABLE $table ( $schema $pk )"
          val statement = conn.createStatement
          try {
            statement.executeUpdate(sql)
          } finally {
            statement.close()
          }
        }
      } finally {
        conn.close()
      }

      //todo: make this a single method
      idColumn match {
        case Some(id) => UpsertUtils.upsert(df, idColumn, jdbcOptions, isCaseSensitive)
        case None => JdbcUtils.saveTable(df, Some(df.schema), isCaseSensitive, options = writeOption)
      }

    }

  }

}
