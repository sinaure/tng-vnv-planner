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

package com.github.tng.vnv.planner.service

import com.github.tng.vnv.planner.model.NetworkService
import com.github.tng.vnv.planner.model.NetworkServiceDescriptor
import com.github.tng.vnv.planner.model.Package
import com.github.tng.vnv.planner.model.Test
import com.github.tng.vnv.planner.model.TestDescriptor
import com.github.tng.vnv.planner.repository.TestPlanRepository
import com.github.tng.vnv.planner.model.TestPlan
import com.github.tng.vnv.planner.utils.TEST_PLAN_STATUS
import groovy.util.logging.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import static org.springframework.util.StringUtils.isEmpty

@Log
@Service
class TestPlanService {

    @Autowired
    TestPlanRepository testPlanRepository
    @Autowired
    TestService testService
    @Autowired
    NetworkServiceService networkServiceService
    @Autowired
    CatalogueService catalogueService

    Set<TestPlan> createByService(NetworkService service) {
        def testPlans = [] as HashSet
        service = networkServiceService.findByUuid(service.uuid)?.loadDescriptor()
        testService.findByService(service)?.each { test ->
            if( !isEmpty(service.uuid) && !isEmpty(test.uuid))
                testPlans.add(new TestPlan(uuid: service.uuid+test.uuid, serviceUuid: service.uuid, testUuid: test.uuid, nsd:service.nsd, testd:test.testd, status: TEST_PLAN_STATUS.CREATED))
        }
        new ArrayList<TestPlan>(testPlans)
    }
    
    Set<TestPlan> createByServiceDescriptor(NetworkServiceDescriptor service) {
        def testPlans = [] as HashSet
        service = networkServiceService.findByUuid(service.uuid)
        testService.findByService(service)?.each { test ->
            if( !isEmpty(service.uuid) && !isEmpty(test.uuid))
                testPlans.add(new TestPlan(uuid: service.uuid+test.uuid, nsd:service.nsd, testd:test.testd, status: TEST_PLAN_STATUS.CREATED))
        }
        testPlans
    }

    Set<TestPlan> createByTest(Test test) {
        def testPlans = [] as HashSet
        test = testService.findByUuid(test.uuid)?.loadDescriptor()
        networkServiceService.findByTest(test)?.each { service ->
            if(!isEmpty(service.uuid) && !isEmpty(test.uuid))
                testPlans.add(new TestPlan(uuid: service.uuid+test.uuid, serviceUuid: service.uuid, testUuid: test.uuid, nsd:service.nsd, testd:test.testd, status: TEST_PLAN_STATUS.CREATED))
        }
        testPlans
    }
    
    Set<TestPlan> createByTestDescriptor(TestDescriptor test) {
        def testPlans = [] as HashSet
        test = testService.findByUuid(test.uuid)
        networkServiceService.findByTest(test)?.each { service ->
            if(!isEmpty(service.uuid) && !isEmpty(test.uuid))
                testPlans.add(new TestPlan(uuid: service.uuid+test.uuid, nsd:service.nsd, testd:test.testd, status: TEST_PLAN_STATUS.CREATED))
        }
        testPlans
    }

    Set<TestPlan> createByServices(Set<NetworkService> nss) {
        def testPlans = [] as HashSet
        nss?.each { 
            it -> testPlans.addAll(createByService(it)) 
            }
        testPlans
    }

    Set<TestPlan> createByTests(Set<Test> ts) {
        def testPlans = [] as HashSet
        ts?.each { it -> testPlans.addAll(createByTest(it)) }
        testPlans
    }

    Set<TestPlan> createByServicesAndByTests(Set nss, Set ts) {
        def testPlans = [] as HashSet
        testPlans.addAll(createByServices(nss))
        testPlans.addAll(createByTests(ts))
        testPlans
    }

    Set<TestPlan> createByPackage(Package pack){
        catalogueService.createByPackage(pack)
    }

    TestPlan save(TestPlan testPlan){
        log.info("#~#vnvlog save STR [test_plan_uuid: ${testPlan?.uuid}, status: ${testPlan?.status} ]")
        testPlan = testPlanRepository.save(testPlan)
        log.info("#~#vnvlog save END [test_plan_uuid: ${testPlan?.uuid}, status: ${testPlan?.status} ]")
        testPlan
    }

    TestPlan update(String uuid, String status) {
        log.info("#~#vnvlog update STR [test_plan_uuid: ${uuid}, status: ${status} ]")
        TestPlan testPlan = findByUuid(uuid)
        testPlan.status = status
        testPlan = testPlanRepository.save(testPlan)
        log.info("#~#vnvlog update END [test_plan_uuid: ${uuid}, status: ${status} ]")
        testPlan
    }

    void delete(String uuid) {
        update(uuid, TEST_PLAN_STATUS.CANCELLING)
    }

    TestPlan findByUuid(String uuid){
        testPlanRepository.findByUuid(uuid)
    }

    TestPlan findNextScheduledTestPlan() {
        testPlanRepository.findFirstByStatus(TEST_PLAN_STATUS.SCHEDULED)
    }

    boolean existsByStartingStatus() {
        (testPlanRepository.findFirstByStatus(TEST_PLAN_STATUS.STARTING) != null)
    }

    List<TestPlan> findAll(){
        testPlanRepository.findAll()
    }
}


