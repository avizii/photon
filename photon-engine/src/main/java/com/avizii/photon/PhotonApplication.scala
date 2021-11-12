package com.avizii.photon

import com.avizii.photon.explain.ExplainFactory
import com.avizii.photon.runtime.SparkRuntime
import com.avizii.photon.util.ParamsUtil
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * @author : Avizii
 */
@SpringBootApplication
class PhotonApplication

object PhotonApplication extends App {
  val params = ParamsUtil.parse(args)
  ExplainFactory.init()
  SparkRuntime.create(params)
  SpringApplication.run(classOf[PhotonApplication], args: _*)
}

