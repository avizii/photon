package com.avizii.backup.photon.common

trait MLSQLPlatformLifecycle {

  def beforeRuntime(params: Map[String, String]): Unit

  def afterRuntime(runtime: StreamingRuntime, params: Map[String, String]): Unit

  def beforeDispatcher(runtime: StreamingRuntime, params: Map[String, String]): Unit

  def afterDispatcher(runtime: StreamingRuntime, params: Map[String, String]): Unit
}
