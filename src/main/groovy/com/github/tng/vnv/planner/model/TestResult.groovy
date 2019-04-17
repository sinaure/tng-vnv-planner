package com.github.tng.vnv.planner.model

import io.swagger.annotations.ApiModelProperty

import javax.validation.constraints.NotNull

class TestResult {

    @ApiModelProperty(
            value = 'Test uuid',
            allowEmptyValue = false,
            required = true)
    @NotNull
    String testUuid

    @ApiModelProperty(
            value = 'Test result uuid',
            allowEmptyValue = false,
            required = true)
    @NotNull
    String testResultUuid

    @ApiModelProperty(
            value = 'Test Plan Status',
            allowEmptyValue = false,
            example = 'STARTING, COMPLETED, CANCELLING, CANCELLED, ERROR',
            required = true)
    @NotNull
    String testStatus
}
