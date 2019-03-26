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

package com.github.tng.vnv.planner.repository


import com.github.tng.vnv.planner.model.TestPlan
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import groovy.util.logging.Log

import static com.github.tng.vnv.planner.utils.DebugHelper.callExternalEndpoint

@Log
@Repository
class TestPlanRestRepository {

    @Autowired
    @Qualifier('restTemplateWithAuth')
    RestTemplate restTemplate

    @Autowired
    @Qualifier('restTemplateWithAuth')
    RestTemplate restTemplateWithAuth

    @Value('${app.tpr.test.plan.create.endpoint}')
    def testPlanCreateEndpoint

    @Value('${app.tpr.test.plan.update.endpoint}')
    def testPlanUpdateEndpoint

    @Value('${app.vnvgk.test.list.by.tag.endpoint}')
    def testListByTagEndpoint

    @Value('${app.gk.service.list.by.tag.endpoint}')
    def serviceListByTagEndpoint

    TestPlan create(def testPlan) {
        def headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        def entity = new HttpEntity<TestPlan>(testPlan ,headers)
        callExternalEndpoint(restTemplate.postForEntity(testPlanCreateEndpoint,entity,TestPlan),'TestResultRepository.createTestPlan',testPlanCreateEndpoint).body
    }

    TestPlan update(def testPlan) {
        def headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        def entity = new HttpEntity<TestPlan>(testPlan ,headers)
        callExternalEndpoint(restTemplate.exchange(testPlanUpdateEndpoint, HttpMethod.PUT, entity, TestPlan.class ,testPlan.uuid),'TestResultRepository.updatePlan',testPlanUpdateEndpoint).body
    }
}
