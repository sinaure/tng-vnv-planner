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

package com.github.tng.vnv.planner.oldlcm.restclient


import com.github.tng.vnv.planner.oldlcm.model.TestPlanOld
import com.github.tng.vnv.planner.oldlcm.model.TestSuiteResult
import groovy.util.logging.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

import static com.github.tng.vnv.planner.helper.DebugHelper.callExternalEndpoint

@Log
@Component
class Executor {

    @Autowired
    @Qualifier('restTemplateWithAuth')
    RestTemplate restTemplate

    @Value('${app.tee.suite.execute.endpoint}')
    def suiteExecuteEndpoint

    TestPlanOld executeTests(TestPlanOld testPlan) {
        def planStatus = 'SUCCESS'
        def results=[]
        testPlan.testSuiteResults.each { testSuiteResult ->
            testSuiteResult.testPlanId=testPlan.uuid
            log.info("##vnvlog Executor.executeTests: ($testPlan)")
            log.info("##vnvlog Executor.executeTests - testPlan.networkServiceInstances.first().instanceUuid: ${testPlan.networkServiceInstances.first().instanceUuid}")
            testSuiteResult.instanceUuid=testPlan.networkServiceInstances.first().instanceUuid
            log.info("##vnvlog Executor.executeTests - testPlan.networkServiceInstances.first().serviceUuid: ${testPlan.networkServiceInstances.first().serviceUuid}")
            testSuiteResult.serviceUuid=testPlan.networkServiceInstances.first().serviceUuid
            log.info("##vnvlog-v.2 Executor.executeTests - begin POST_request_to_TEE with testSuiteResult: $testSuiteResult")
            testSuiteResult = callExternalEndpoint(restTemplate.postForEntity(suiteExecuteEndpoint, testSuiteResult, TestSuiteResult),
                    'Executor.executeTests',suiteExecuteEndpoint).body
            log.info("##vnvlog-v.2 Executor.executeTests - end POST_request_to_TEE with testSuiteResult.uuid: ${testSuiteResult.uuid}")
            planStatus = planStatus == 'SUCCESS' ? testSuiteResult.status : planStatus
            results << testSuiteResult
        }
        testPlan.testSuiteResults=results
        testPlan.status = planStatus
        testPlan
    }
}
