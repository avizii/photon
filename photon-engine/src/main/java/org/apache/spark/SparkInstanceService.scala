package org.apache.spark

import org.apache.spark.scheduler.cluster.{CoarseGrainedSchedulerBackend, StandaloneSchedulerBackend}
import org.apache.spark.scheduler.local.LocalSchedulerBackend
import org.apache.spark.sql.SparkSession

class SparkInstanceService(session: SparkSession) {

  def resources = {
    var totalTasks = 0l
    var totalUsedMemory = 0l
    var totalMemory = 0l
    session.sparkContext.statusTracker.getExecutorInfos.map { worker =>
      totalTasks += worker.numRunningTasks()
      totalUsedMemory += (worker.usedOnHeapStorageMemory() + worker.usedOffHeapStorageMemory())
      totalMemory += (worker.totalOnHeapStorageMemory() + worker.totalOffHeapStorageMemory())

    }
    val totalCores = session.sparkContext.schedulerBackend match {
      case sb if sb.isInstanceOf[CoarseGrainedSchedulerBackend] =>
      //        ReflectHelper.field(sb, "totalCoreCount").asInstanceOf[AtomicInteger].get()
      case sb if sb.isInstanceOf[LocalSchedulerBackend] =>
        java.lang.Runtime.getRuntime.availableProcessors
      case sb if sb.isInstanceOf[StandaloneSchedulerBackend] => -1
    }
    //    SparkInstanceResource(totalCores.toLong, totalTasks, totalUsedMemory, totalMemory)
    SparkInstanceResource(null, totalTasks, totalUsedMemory, totalMemory)
  }

}

case class SparkInstanceResource(totalCores: Long, totalTasks: Long, totalUsedMemory: Long, totalMemory: Long)
