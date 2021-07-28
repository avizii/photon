package com.avizii.glint.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
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
