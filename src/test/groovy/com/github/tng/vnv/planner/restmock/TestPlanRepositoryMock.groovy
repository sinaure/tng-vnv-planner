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

package com.github.tng.vnv.planner.restmock


import com.github.tng.vnv.planner.oldlcm.model.TestPlanOld
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestPlanRepositoryMock {

    Map<String, TestPlanOld> testPlans = [:]
    def numOfCallsForTestPlanCreation = 0
    def numOfCallsForTestPlanUpdate = 0

    void reset() {
        testPlans.clear()
    }

    @GetMapping('/mock/trr/test-plans')
    List listTestPlans() {
        new ArrayList(testPlans.values())
    }

    @PostMapping('/mock/trr/test-plans')
    TestPlanOld createTestPlan(@RequestBody TestPlanOld testPlan) {
        ++numOfCallsForTestPlanCreation
        testPlan.uuid = UUID.randomUUID().toString()
        testPlans[testPlan.uuid] = testPlan
    }

    @PutMapping('/mock/trr/test-plans/{testPlanId:.+}')
    TestPlanOld updatePlan(@RequestBody TestPlanOld testPlan, @PathVariable('testPlanId') String testPlanId) {
        ++numOfCallsForTestPlanUpdate
         testPlan.uuid = testPlanId
        testPlans[testPlan.uuid] = testPlan
    }
}
