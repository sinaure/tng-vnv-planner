/*
 * Copyright (c) 2015 SONATA-NFV, 2017 5GTANGO [, ANY ADDITIONAL AFFILIATION]
 * ALL RIGHTS RESERVED.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Neither the name of the SONATA-NFV, 5GTANGO [, ANY ADDITIONAL AFFILIATION]
 * nor the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * This work has been performed in the framework of the SONATA project,
 * funded by the European Commission under Grant number 671517 through
 * the Horizon 2020 and 5G-PPP programmes. The authors would like to
 * acknowledge the contributions of their colleagues of the SONATA
 * partner consortium (www.sonata-nfv.eu).
 *
 * This work has been performed in the framework of the 5GTANGO project,
 * funded by the European Commission under Grant number 761493 through
 * the Horizon 2020 and 5G-PPP programmes. The authors would like to
 * acknowledge the contributions of their colleagues of the 5GTANGO
 * partner consortium (www.5gtango.eu).
 */

package com.github.tng.vnv.planner.controller

import com.github.tng.vnv.planner.app.Collector
import com.github.tng.vnv.planner.model.TestPlanCallback
import groovy.util.logging.Log
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

@Log
@RestController
@RequestMapping('/api/v1/test-plans')
class CuratorCallbackController {

    static final String TEST_PLAN_APPROVED_STATUS = 'PAUSED'
    static final String TEST_PLAN_CREATED = 'CREATED'
    static final String TEST_PLAN_CRASHED = 'CRASHED'
    static final String TEST_PLAN_CANCELED = 'CANCELED'
    static final String TEST_PLAN_FINISHED = 'FINISHED'
    static final String TEST_PLAN_RESCHEDULED = 'RESCHEDULED'

    @Autowired
    Collector collector

    @ApiResponses(value = [
            @ApiResponse(code = 400, message = 'Bad Request'),
            @ApiResponse(code = 404, message = 'Could not find package with that package_id'),
    ])
    @PostMapping('/on-change')
    ResponseEntity<Void> onChange(@Valid @RequestBody TestPlanCallback body) {
        //todo-Y2:this endpoint is an on-change callback and is specific to the asynchronous nature of the unpackaging
        log.info("##vnvlog Executor.executeTests request. ")
        switch (body.testPlanStatus) {

            //todo-gandreou: fix this part for every different schenario
            case TEST_PLAN_PAUSED || TEST_PLAN_CREATED:
                collector.update(body.testPlanUuid, body.testPlanStatus)
                break
            case TEST_PLAN_CREATED:
                collector.update(body.testPlanUuid, body.testPlanStatus)
                break
            case TEST_PLAN_CRASHED:
                collector.update(body.testPlanUuid, body.testPlanStatus)
                break
            case TEST_PLAN_CANCELED:
                collector.update(body.testPlanUuid, body.testPlanStatus)
                break
            case TEST_PLAN_FINISHED:
                collector.update(body.testPlanUuid, body.testPlanStatus)
                break
            case TEST_PLAN_POSTPONED:
                collector.update(body.testPlanUuid, body.testPlanStatus)
                break
            default:
                collector.update(body.testPlanUuid, body.testPlanStatus)
        }
        ResponseEntity.ok().build()
    }
}