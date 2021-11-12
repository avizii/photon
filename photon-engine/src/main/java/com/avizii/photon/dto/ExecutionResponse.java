package com.avizii.photon.dto;

import com.avizii.photon.api.BaseResponse;
import lombok.*;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ExecutionResponse extends BaseResponse {

  private ScriptResult execution;
}
