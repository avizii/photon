package org.apache.spark.api

import org.apache.spark.api.plugin.{DriverPlugin, ExecutorPlugin, PluginContext, SparkPlugin}

import _root_.java.util

trait MLSQLExecutorPlugin extends SparkPlugin {

  override def driverPlugin(): DriverPlugin = null

  override def executorPlugin(): ExecutorPlugin = new ExecutorPlugin {
    override def init(ctx: PluginContext, extraConf: util.Map[String, String]): Unit = _init(Map[Any, Any]())

    override def shutdown(): Unit = _shutdown()
  }

  def _init(config: Map[Any, Any]): Unit

  def _shutdown(): Unit

}

