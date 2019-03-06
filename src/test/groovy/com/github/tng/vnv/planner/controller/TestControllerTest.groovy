package com.github.tng.vnv.planner.controller


import com.github.tng.vnv.planner.restmock.TestCatalogueMock
import com.github.tng.vnv.planner.restmock.TestExecutionEngineMock
import com.github.tng.vnv.planner.restmock.TestPlatformManagerMock
import com.github.tng.vnv.planner.restmock.TestResultRepositoryMock
import com.github.mrduguo.spring.test.AbstractSpec
import org.springframework.beans.factory.annotation.Autowired

class TestControllerTest extends AbstractSpec {

    final def TEST_SUITE_ID='input0ts-75f5-4ca1-90c8-12ec80a79821'
    @Autowired
    TestPlatformManagerMock testPlatformManagerMock

    @Autowired
    TestExecutionEngineMock testExecutionEngineMock

    @Autowired
    TestCatalogueMock testCatalogueMock

    @Autowired
    TestResultRepositoryMock testResultRepositoryMock

    void "schedule single TestSuite should produce successfully 1 Result for 1 testPlan"() {

        when:
        def entity = postForEntity('/tng-vnv-planner/api/v1/schedulers/tests',
                ["test_uuid": TEST_SUITE_ID]
                , Void.class)

        then:
        Thread.sleep(10000L);
        while (testPlatformManagerMock.networkServiceInstances.values().last().status!='TERMINATED')
            Thread.sleep(1000L);
        testPlatformManagerMock.networkServiceInstances.size()==3
        testExecutionEngineMock.testSuiteResults.size()==3
        testExecutionEngineMock.testSuiteResults.values().last().status=='SUCCESS'

        testResultRepositoryMock.testPlans.size()==3
        testResultRepositoryMock.testPlans.values().last().status=='SUCCESS'
        testResultRepositoryMock.testPlans.values().last().networkServiceInstances.size()==1
        testResultRepositoryMock.testPlans.values().last().testSuiteResults.last().status=='SUCCESS'

        cleanup:
        testPlatformManagerMock.reset()
        testExecutionEngineMock.reset()
        testResultRepositoryMock.reset()

    }

    void "retrieval of a single network service's related NetworkServices should successfully all the tag related services "() {
        when:
        List nss = getForEntity('/tng-vnv-planner/api/v1/schedulers/tests/{testUuid}/services', List,TEST_SUITE_ID).body
        then:

        nss.size() == 3

        cleanup:
        testPlatformManagerMock.reset()

    }
}
