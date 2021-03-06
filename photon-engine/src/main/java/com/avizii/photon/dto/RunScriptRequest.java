package com.avizii.photon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RunScriptRequest {

  @NotEmpty private String sql;

  private String token;

  private String jobType; // script/stream/sql

  private String executeMode; // query/analyze

  private String jobName;

  private String jobId; // job identify - uuid

  private Long timeout;

  private Boolean silence; // last sql in script return nothing

  private String sessionType; // global/per_user/per_request

  private Boolean async;

  private String callbackUrl;

  private Boolean skipInclude;

  private Boolean skipAuth;

  private Boolean skipGrammarValidate;

  private Boolean skipPhysical;

  private Boolean includeSchema; // return schema info

  private Boolean onlySchema;

  private String fetchType; // take/collect

  private Integer fetchSize; // take size data

  private Boolean enableQueryWithIndexer; // query with indexer to speed up
}
