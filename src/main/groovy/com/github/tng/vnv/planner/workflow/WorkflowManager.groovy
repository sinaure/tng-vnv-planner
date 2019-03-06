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

package com.github.tng.vnv.planner.workflow


import com.github.tng.vnv.planner.model.NetworkService
import com.github.tng.vnv.planner.oldlcm.model.NetworkServiceInstance
import com.github.tng.vnv.planner.oldlcm.model.TestPlanOld
import com.github.tng.vnv.planner.oldlcm.model.TestSuiteOld
import com.github.tng.vnv.planner.oldlcm.model.TestSuiteResult
import com.github.tng.vnv.planner.repository.TestPlanRepository
import com.github.tng.vnv.planner.oldlcm.restclient.Executor
import com.github.tng.vnv.planner.restclient.Curator
import groovy.util.logging.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Log
@Component
class WorkflowManager {

    @Autowired
    TestPlanRepository testPlanRepository

    @Autowired
    Curator curator

    @Autowired
    Executor executor

    boolean execute(NetworkService networkService, Collection<TestSuiteOld> testSuites) {
        log.info('##vnvlog: before createTestPlan: [not created yet]')
        def testPlan = createTestPlan(networkService, testSuites)
        log.info("##vnvlog: after createTestPlan: ${testPlan.uuid}")
        log.info('##vnvlog: before deployNs: [not created yet]')
        testPlan = deployNsForTest(testPlan)
        log.info("##vnvlog: after deployNs (#${testPlan.networkServiceInstances.size()}): ${testPlan.networkServiceInstances.first().instanceUuid}, status: ${testPlan.networkServiceInstances.first().status}")
        if(testPlan.status=='NS_DEPLOYED'){
            log.info('##vnvlog: before executeTests with testSuiteResults: [not created yet]')
            testPlan = executeTests(testPlan)
            log.info("##vnvlog: after executeTests with testSuiteResults(#${testPlan.testSuiteResults.size()})")
            return (destroyNsAfterTest(testPlan).status == 'SUCCESS')
        }
        false
    }

    TestPlanOld createTestPlan(NetworkService networkService, Collection<TestSuiteOld> testSuites) {
        log.info("##vnvlog: (networkServiceId: $networkService.networkServiceId, testListSize: ${testSuites?.size()}, " +
                "networkService.name: $networkService.nsd.name)")
        log.info("##vnvlog: issue!:testSuites.first()?.packageId: ${testSuites?.first()?.packageId}")
        def testPlanUuid = UUID.randomUUID().toString()
        def nsi = new NetworkServiceInstance(
                name: "vnv-test-${networkService.nsd.name.toLowerCase().replaceAll(' ','-')}",
                serviceUuid: networkService.networkServiceId)
        def testPlan = new TestPlanOld(
                uuid: testPlanUuid,
                packageId: testSuites.first().packageId,
                networkServiceInstances: [nsi],
                testSuiteResults: testSuites.collect {testSuite->
                    new TestSuiteResult(
                            uuid: UUID.randomUUID().toString(),
                            testUuid: testSuite.testUuid,
                            packageId: testSuite.packageId,
                            serviceUuid: networkService.networkServiceId,
                            testPlanId: testPlanUuid
                    )
                },
                status: 'CREATED',
        )
        TestPlanOld tpo = testPlanRepository.createTestPlan(testPlan)
        tpo
    }

    TestPlanOld deployNsForTest(TestPlanOld testPlan) {
        testPlan = curator.deployNsForTest(testPlan)
        testPlanRepository.updateTestPlan(testPlan)
    }

    TestPlanOld executeTests(TestPlanOld testPlan) {
        testPlan = executor.executeTests(testPlan)
        testPlanRepository.updateTestPlan(testPlan)
    }

    TestPlanOld destroyNsAfterTest(TestPlanOld testPlan) {
        testPlan = curator.destroyNsAfterTest(testPlan)
        testPlanRepository.updateTestPlan(testPlan)
    }
}
