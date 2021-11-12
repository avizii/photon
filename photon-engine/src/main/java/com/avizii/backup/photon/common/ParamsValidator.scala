package com.avizii.backup.photon.common

import java.util

trait ParamsValidator {
  def valid(params: util.Map[Any, Any]): (Boolean, String)
}
