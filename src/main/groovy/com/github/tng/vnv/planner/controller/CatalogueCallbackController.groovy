/*
 * Copyright (c) 2015 SONATA-NFV, 2019 5GTANGO [, ANY ADDITIONAL AFFILIATION]
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

import com.github.tng.vnv.planner.aspect.TriggerNextTestPlan
import com.github.tng.vnv.planner.model.TestPlan
import org.springframework.web.bind.annotation.ResponseBody

import javax.validation.Valid

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.github.tng.vnv.planner.ScheduleManager
import com.github.tng.vnv.planner.model.PackageCallback
import groovy.util.logging.Log
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

@Log
@RestController
@RequestMapping('/api/v1/packages')
class CatalogueCallbackController {

    static final String PACKAGE_DELETED = 'DELETED'
    static final String PACKAGE_CREATED = 'CREATED'

    @Autowired
    ScheduleManager scheduler

    @TriggerNextTestPlan
    @ApiResponses(value = [
            @ApiResponse(code = 400, message = 'Bad Request'),
            @ApiResponse(code = 404, message = 'Could not find package with that package_id'),
    ])
    @PostMapping('/on-change')
    @ResponseBody
    List<TestPlan> onChange(@Valid @RequestBody PackageCallback body) {
        log.info("#~#vnvlog onChange STR [PackageId: ${body?.packageId}]")
        List<TestPlan> testPlans = scheduler.create(body.packageId)
        log.info("#~#vnvlog onChange: END [PackageId: ${body?.packageId}]")
        testPlans
    }

}
