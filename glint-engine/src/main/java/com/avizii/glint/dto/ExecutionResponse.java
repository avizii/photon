package com.avizii.glint.dto;

import com.avizii.glint.api.BaseResponse;
import lombok.*;

/** @Author : Avizii @Create : 2021.05.17 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ExecutionResponse extends BaseResponse {

  private ScriptResult execution;
}
