package com.github.tng.vnv.planner.controller

import com.github.tng.vnv.planner.restmock.TestCatalogueMock
import com.github.tng.vnv.planner.restmock.TestExecutionEngineMock
import com.github.tng.vnv.planner.restmock.TestPlatformManagerMock
import com.github.tng.vnv.planner.restmock.TestResultRepositoryMock
import com.github.mrduguo.spring.test.AbstractSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping

import spock.lang.Ignore

class NetworkControllerTest extends AbstractSpec {

    final def NETWORK_SERVICE_ID = 'input0ns-f213-4fae-8d3f-04358e1e1445'

    final def NETWORK_SERVICE_HTTP_ADVANCED_ID='d07742ed-9429-4a07-b7af-d0b24a6d5c4c'
    final def TEST_HTTP_ADVANCED_ID='aa5c779a-8cc7-47a9-9112-d2ff348898b4'
    final def PACKAGE_OF_TEST_HTTP_ADVANCED_ID='a3acb16d-c314-4122-9b3d-9c180547d580'
	final def TEST_SUITE_TAG='aux_test'

    @Autowired
    TestPlatformManagerMock testPlatformManagerMock

    @Autowired
    TestExecutionEngineMock testExecutionEngineMock

    @Autowired
    TestCatalogueMock testCatalogueMock

    @Autowired
    TestResultRepositoryMock testResultRepositoryMock

    void "schedule single NetworkService should produce successfully 1 Result for 1 testPlan"() {

        when:
        def entity = postForEntity('/tng-vnv-planner/api/v1/schedulers/services',
                ["service_uuid": NETWORK_SERVICE_ID]
                , Void.class)


        then:
        Thread.sleep(10000L);
        while (testExecutionEngineMock.testSuiteResults.values().last().status!='SUCCESS')
            Thread.sleep(1000L);
        testPlatformManagerMock.networkServiceInstances.size()==1

        testExecutionEngineMock.testSuiteResults.size()==1

        testResultRepositoryMock.testPlans.size()==1
        testResultRepositoryMock.testPlans.values().last().status=='SUCCESS'
        testResultRepositoryMock.testPlans.values().last().networkServiceInstances.size()==1
        testResultRepositoryMock.testPlans.values().each{testPlan ->
            testPlan.testSuiteResults.size()==1
        }
        testResultRepositoryMock.testPlans.values().last().testSuiteResults.last().status=='SUCCESS'

        cleanup:
        testPlatformManagerMock.reset()
        testExecutionEngineMock.reset()
        testResultRepositoryMock.reset()
    }

    void "retrieval of a single test suite's related testSuites should successfully all the tag related tests"() {
        when:
        List tss = getForEntity('/tng-vnv-planner/api/v1/schedulers/services/{serviceUuid}/tests', List, NETWORK_SERVICE_ID).body
        then:

        tss.size() == 1
        cleanup:
        testPlatformManagerMock.reset()

    }

    void "schedule Service GOBETWEEN with tag: http-advanced should run once with TEST-HTTP-BENCHMARK-ADVANCED with 1 Result and 1 testPlan"() {

        when:
        def entity = postForEntity('/tng-vnv-planner/api/v1/schedulers/services',
                ["service_uuid": NETWORK_SERVICE_HTTP_ADVANCED_ID]
                , Void.class)

        then:
        Thread.sleep(10000L);
        while (testPlatformManagerMock.networkServiceInstances.values().last().status!='TERMINATED')
            Thread.sleep(1000L);
        testPlatformManagerMock.networkServiceInstances.size()==1


        testExecutionEngineMock.testSuiteResults.size()==1
        testExecutionEngineMock.testSuiteResults.values().testUuid[0] == TEST_HTTP_ADVANCED_ID
        testExecutionEngineMock.testSuiteResults.values().last().status=='SUCCESS'

        testResultRepositoryMock.testPlans.size()==1
        testResultRepositoryMock.testPlans.values().last().status=='SUCCESS'
        testResultRepositoryMock.testPlans.values().last().networkServiceInstances.size()==1
        testResultRepositoryMock.testPlans.values().last().testSuiteResults.packageId[0]==PACKAGE_OF_TEST_HTTP_ADVANCED_ID
        testResultRepositoryMock.testPlans.values().last().testSuiteResults.last().status=='SUCCESS'

        cleanup:
        testPlatformManagerMock.reset()
        testExecutionEngineMock.reset()
        testResultRepositoryMock.reset()
    }

    @Ignore
    void "when one service is related with one test should run only once"() {

        when:
        def entity = postForEntity('/tng-vnv-planner/api/v1/schedulers/services',
                ["service_uuid": NETWORK_SERVICE_HTTP_ADVANCED_ID]
                , Void.class)

        then:
        Thread.sleep(10000L);
        while (testPlatformManagerMock.networkServiceInstances.values().last().status!='TERMINATED')
            Thread.sleep(1000L);

        testPlatformManagerMock.networkServiceInstances.size()==1
        testExecutionEngineMock.testSuiteResults.size()==1

        //fixme: how could num of executions be 11
        testExecutionEngineMock.numOfExecutions == 1
        //fixme: how could num of calls for the TestPlanCreation be 9
        testResultRepositoryMock.numOfCallsForTestPlanCreation == 1
        //fixme: how could num of calls for the TestPlanUpdate be 27
        testResultRepositoryMock.numOfCallsForTestPlanUpdate == 3

        testResultRepositoryMock.testPlans.size()==1

        cleanup:
        testPlatformManagerMock.reset()
        testExecutionEngineMock.reset()
        testResultRepositoryMock.reset()
    }
	
	void "retrieval of a list of NetworkServices whith tag existing on tags list"() {
		when:
		List nss = getForEntity('/tng-vnv-planner/api/v1/schedulers/services/testTag/{testTag}/services', List,TEST_SUITE_TAG).body
		then:

		nss.size() == 1

		cleanup:
		testPlatformManagerMock.reset()

	}
	
}