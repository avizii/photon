package com.avizii.backup.photon.common

import java.util.{Map => JMap}

trait StreamingRuntime {

  def startRuntime: StreamingRuntime

  def destroyRuntime(stopGraceful: Boolean, stopContext: Boolean = false): Boolean

  def streamingRuntimeInfo: StreamingRuntimeInfo

  def resetRuntimeOperator(runtimeOperator: RuntimeOperator): Unit

  def configureStreamingRuntimeInfo(streamingRuntimeInfo: StreamingRuntimeInfo): Unit

  def awaitTermination(): Unit

  def startThriftServer(): Unit

  def startHttpServer(): Unit

  def params: JMap[Any, Any]

  def afterRuntimeStarted(): Unit
}

trait StreamingRuntimeInfo

trait Event

case class JobFlowGenerate(jobName: String, index: Int) extends Event

trait PlatformManagerListener {
  def processEvent(event: Event): Unit
}
