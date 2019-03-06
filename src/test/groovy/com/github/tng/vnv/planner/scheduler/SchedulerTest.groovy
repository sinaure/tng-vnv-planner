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

package com.github.tng.vnv.planner.scheduler


import com.github.tng.vnv.planner.model.PackageMetadata
import com.github.tng.vnv.planner.restmock.TestCatalogueMock
import com.github.tng.vnv.planner.oldlcm.restmock.ExecutorMock
import com.github.tng.vnv.planner.restmock.CuratorMock
import com.github.tng.vnv.planner.restmock.TestPlanRepositoryMock
import com.github.mrduguo.spring.test.AbstractSpec
import org.springframework.beans.factory.annotation.Autowired

import java.util.concurrent.CompletableFuture

class SchedulerTest extends AbstractSpec {

    public static final String MULTIPLE_TEST_PLANS_PACKAGE_ID ='multiple_scheduler:test:0.0.1'

    @Autowired
    Scheduler scheduler

    @Autowired
    CuratorMock curatorMock

    @Autowired
    ExecutorMock executorMock

    @Autowired
    TestCatalogueMock testCatalogueMock

    @Autowired
    TestPlanRepositoryMock testPlanRepositoryMock

    void 'schedule multiple test plans should produce success result'() {

        when:
        CompletableFuture<Boolean> out = scheduler.schedule(new PackageMetadata(packageId: MULTIPLE_TEST_PLANS_PACKAGE_ID))

        then:
        Thread.sleep(10000L);
        while (executorMock.testSuiteResults.values().last().status!='SUCCESS')
            Thread.sleep(1000L);
        out.get() == true
        curatorMock.networkServiceInstances.size()==3
        executorMock.testSuiteResults.size()==3
        executorMock.testSuiteResults.values().last().status=='SUCCESS'

        testPlanRepositoryMock.testPlans.size()==3
        testPlanRepositoryMock.testPlans.values().last().status=='SUCCESS'
        testPlanRepositoryMock.testPlans.values().last().networkServiceInstances.size()==1
        testPlanRepositoryMock.testPlans.values().each{testPlan ->
            testPlan.testSuiteResults.size()==2
        }
        testPlanRepositoryMock.testPlans.values().last().testSuiteResults.last().status=='SUCCESS'

        cleanup:
        curatorMock.reset()
        executorMock.reset()
        testPlanRepositoryMock.reset()
    }
}