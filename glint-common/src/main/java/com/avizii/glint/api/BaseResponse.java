package com.avizii.glint.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author : Avizii
 * @Create : 2021.05.17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse {
    private String message;
    @Builder.Default
    private ResultCode code = ResultCode.SUCCESS;

    public boolean isSuccess() {
        return code == ResultCode.SUCCESS;
    }
}
