package com.avizii.glint.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @Author : Avizii @Create : 2021.05.17 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScriptResult {

  private String fetchType;

  private Long fetchSize;

  private Boolean includeSchema;

  private String dataJson;

  private String schemaJson;
}
