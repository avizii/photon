package com.avizii.photon.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse {
  private String message;
  @Builder.Default private ResultCode code = ResultCode.SUCCESS;

  public boolean isSuccess() {
    return code == ResultCode.SUCCESS;
  }
}
