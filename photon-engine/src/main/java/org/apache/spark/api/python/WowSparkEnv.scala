package org.apache.spark.api.python

import org.apache.spark.broadcast.BroadcastManager
import org.apache.spark.memory.MemoryManager
import org.apache.spark.metrics.MetricsSystem
import org.apache.spark.rpc.RpcEnv
import org.apache.spark.scheduler.OutputCommitCoordinator
import org.apache.spark.serializer._
import org.apache.spark.shuffle.ShuffleManager
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.BlockManager
import org.apache.spark.util.Utils
import org.apache.spark.{MapOutputTracker, SecurityManager, SparkConf, SparkEnv}

import java.io._
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.util.UUID
import scala.reflect.ClassTag

class WowSparkEnv(
                  executorId: String,
                  rpcEnv: RpcEnv,
                  serializer: Serializer,
                  closureSerializer: Serializer,
                  serializerManager: SerializerManager,
                  mapOutputTracker: MapOutputTracker,
                  shuffleManager: ShuffleManager,
                  broadcastManager: BroadcastManager,
                  blockManager: BlockManager,
                  securityManager: SecurityManager,
                  metricsSystem: MetricsSystem,
                  memoryManager: MemoryManager,
                  outputCommitCoordinator: OutputCommitCoordinator,
                  conf: SparkConf) extends SparkEnv(
  executorId: String,
  rpcEnv: RpcEnv,
  serializer: Serializer,
  closureSerializer: Serializer,
  serializerManager: SerializerManager,
  mapOutputTracker: MapOutputTracker,
  shuffleManager: ShuffleManager,
  broadcastManager: BroadcastManager,
  blockManager: BlockManager,
  securityManager: SecurityManager,
  metricsSystem: MetricsSystem,
  memoryManager: MemoryManager,
  outputCommitCoordinator: OutputCommitCoordinator,
  conf: SparkConf) {


}

object LocalNonOpSerializerInstance {
  val maps = new java.util.concurrent.ConcurrentHashMap[String, AnyRef]()
}

class LocalNonOpSerializerInstance(javaD: SerializerInstance) extends SerializerInstance {

  private def isClosure(cls: Class[_]): Boolean = {
    cls.getName.contains("$anonfun$")
  }

  override def serialize[T: ClassTag](t: T): ByteBuffer = {
    if (isClosure(t.getClass)) {
      val uuid = UUID.randomUUID().toString
      LocalNonOpSerializerInstance.maps.put(uuid, t.asInstanceOf[AnyRef])
      ByteBuffer.wrap(uuid.getBytes())
    } else {
      javaD.serialize(t)
    }

  }

  override def deserialize[T: ClassTag](bytes: ByteBuffer): T = {
    val s = StandardCharsets.UTF_8.decode(bytes).toString()
    if (LocalNonOpSerializerInstance.maps.containsKey(s)) {
      LocalNonOpSerializerInstance.maps.remove(s).asInstanceOf[T]
    } else {
      bytes.flip()
      javaD.deserialize(bytes)
    }

  }

  override def deserialize[T: ClassTag](bytes: ByteBuffer, loader: ClassLoader): T = {
    val s = StandardCharsets.UTF_8.decode(bytes).toString()
    if (LocalNonOpSerializerInstance.maps.containsKey(s)) {
      LocalNonOpSerializerInstance.maps.remove(s).asInstanceOf[T]
    } else {
      bytes.flip()
      javaD.deserialize(bytes, loader)
    }
  }

  override def serializeStream(s: OutputStream): SerializationStream = {
    javaD.serializeStream(s)
  }

  override def deserializeStream(s: InputStream): DeserializationStream = {
    javaD.deserializeStream(s)
  }
}

class LocalNonOpSerializer(conf: SparkConf) extends Serializer with Externalizable {
  val javaS = new JavaSerializer(conf)

  override def newInstance(): SerializerInstance = {
    new LocalNonOpSerializerInstance(javaS.newInstance())
  }

  override def writeExternal(out: ObjectOutput): Unit = Utils.tryOrIOException {
    javaS.writeExternal(out)
  }

  override def readExternal(in: ObjectInput): Unit = Utils.tryOrIOException {
    javaS.readExternal(in)
  }
}

object WowSparkEnv {

  private def createSparkEnv = {
    val env = SparkEnv.get
    new WowSparkEnv(
      env.executorId: String,
      env.rpcEnv: RpcEnv,
      env.serializer: Serializer,
      new LocalNonOpSerializer(env.conf): Serializer,
      env.serializerManager: SerializerManager,
      env.mapOutputTracker: MapOutputTracker,
      env.shuffleManager: ShuffleManager,
      env.broadcastManager: BroadcastManager,
      env.blockManager: BlockManager,
      env.securityManager: SecurityManager,
      env.metricsSystem: MetricsSystem,
      env.memoryManager: MemoryManager,
      env.outputCommitCoordinator: OutputCommitCoordinator,
      env.conf: SparkConf)
  }

  def enhanceSparkEnvForAPIService(session: SparkSession) = {
    SparkEnv.set(createSparkEnv)
  }
}
