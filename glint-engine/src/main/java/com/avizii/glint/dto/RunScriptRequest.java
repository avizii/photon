package com.avizii.glint.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @Author : Avizii
 * @Create : 2021.05.17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RunScriptRequest {

    @NotEmpty
    private String sql;

    private String token;

    private String jobType;   // script/stream/sql

    private String executeMode;  // query/analyze

    private String jobId;  // job identify - uuid

    private Long timeout;

    private Boolean silence; // last sql in script return nothing

    private String sessionType; // global/per_user/per_request

    private Boolean async;

    private String callbackUrl;

    private Boolean skipInclude;

    private Boolean skipAuth;

    private Boolean skipGrammarValidate;

    private Boolean includeSchema; // return schema info

    private String fetchType; // take/collect

    private String fetchSize; // take size data

    private Boolean enableQueryWithIndexer; // query with indexer to speed up

}
