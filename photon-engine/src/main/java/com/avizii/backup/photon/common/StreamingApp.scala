package com.avizii.backup.photon.common

import com.avizii.backup.photon.util.{ParamsUtil, TryTool}

import scala.collection.JavaConverters._

object StreamingApp {

  def main(args: Array[String]): Unit = {
    val params = new ParamsUtil(args)
    require(params.hasParam("streaming.name"), "Application name should be set")
    val platform = PlatformManager.getOrCreate
    TryTool.tryOrExit {
      val buildInHooks = List("tech.mlsql.runtime.LogFileHook", "tech.mlsql.runtime.PluginHook")
      val externalHooks = params.getParamsMap.asScala.toMap.get("streaming.platform_hooks").map(item => item.split(",").toList).getOrElse(List())
      (buildInHooks ++ externalHooks).foreach { className =>
        platform.registerMLSQLPlatformLifecycle(
          Class.forName(className).
           newInstance().asInstanceOf[MLSQLPlatformLifecycle])
      }
    }
    platform.run(params)
  }
}

class StreamingApp
