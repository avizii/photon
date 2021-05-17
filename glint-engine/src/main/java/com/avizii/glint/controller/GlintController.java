package com.avizii.glint.controller;

import com.avizii.glint.dto.ExecutionResponse;
import com.avizii.glint.dto.RunScriptRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author : Avizii
 * @Create : 2021.05.14
 */
@RestController
@Slf4j
@Validated
public class GlintController {

    @PostMapping("/runScript")
    public ExecutionResponse runScript(@RequestBody @Valid RunScriptRequest request) {
        return new ExecutionResponse();
    }

}
