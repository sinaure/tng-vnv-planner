package com.github.tng.vnv.planner.controller

import com.github.tng.vnv.planner.config.TestRestSpec
import com.github.tng.vnv.planner.model.TestPlan
import com.github.tng.vnv.planner.restmock.CuratorMock
import com.github.tng.vnv.planner.restmock.DataMock
import com.github.tng.vnv.planner.service.TestPlanService
import com.github.tng.vnv.planner.utils.TEST_PLAN_STATUS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

class TestPlanControllerTest extends TestRestSpec {

    @Autowired
    TestPlanService testPlanService

    @Autowired
    CuratorMock curatorMock

    public static final String IMEDIA_TEST_PLAN_SERVICE_UUID = 'immedia0-9429-4a07-b7af-dd429d6d04o3'
    public static final String IMEDIA_TEST_PLAN_TEST_UUID = 'immedia0-8cc7-47a9-9112-6wff9e88wu2k'
    public static final String LATENCY_TEST_PLAN_SERVICE_UUID = 'input0ns-f213-4fae-8d3f-04358e1e1451'
    public static final String LATENCY_TEST_PLAN_TEST_UUID = 'input0ts-75f5-4ca1-90c8-12ec80a79836'
    public static final String TAG_UNRELATED_TEST_PLAN_SERVICE_UUID = 'input0ns-f213-4fae-8d3f-04358e1e1451'
    public static final String TAG_UNRELATED_TEST_PLAN_TEST_UUID = 'input0ts-75f5-4ca1-90c8-12ec80a79836'
    public static final String DIY_DESCRIPTOR_TEST_PLAN_SERVICE_UUID = '4dd4cb15-76b8-46fd-b3c0-1b165cc332f9'
    public static final String DIY_DESCRIPTOR_TEST_PLAN_TEST_UUID = 'b68dbe19-5c02-4865-8c4b-5e43ada1b67d'
    public static final String DIY_DESCRIPTOR_TEST_PLAN_VALIDATION_REQUIRED_TEST_UUID = 'b68dbe19-5c02-4865-8c4b-5e43ada1b67c'
    public static final String DIY_DESCRIPTOR_TEST_PLAN_CONFIRMED_TEST_UUID = 'b68dbe19-5c02-4865-8c4b-5e43ada1b67b'
    public static final String UNKNOWN_UUID = '00000000-5c02-4865-8c4b-5e43ada1b67b'
     public static final String TEST_DESCRIPTOR_UUID = 'input0ts-75f5-4ca1-90c8-12ec80a79836'
    

    public static final String TEST_PLAN_UUID = '109873681'
    public static final String TEST_PLAN_UUID2 = '109873682'

    void "when curator is busy, schedule request of a test plan list should successfully save all test plans unsorted"() {
        setup:
        curatorMock.isBusy(true)
        when:
        def entity = postForEntity('/api/v1/test-plans',
                [
                        'service_uuid': IMEDIA_TEST_PLAN_SERVICE_UUID,
                        'test_uuid'   : IMEDIA_TEST_PLAN_TEST_UUID,
                        'description' : 'dummyTestPlan1-index1',
                        'index'       : '1',
                ]
                , Void.class)
        entity = postForEntity('/api/v1/test-plans',
                [
                        'service_uuid': LATENCY_TEST_PLAN_SERVICE_UUID,
                        'test_uuid'   : LATENCY_TEST_PLAN_TEST_UUID,
                        'description' : 'dummyTestPlan2-index3',
                        'index'       : '3',
                ]
                , Void.class)
        entity = postForEntity('/api/v1/test-plans',
                [
                        'service_uuid': TAG_UNRELATED_TEST_PLAN_SERVICE_UUID,
                        'test_uuid'   : TAG_UNRELATED_TEST_PLAN_TEST_UUID,
                        'description' : 'dummyTestPlan3-index2',
                        'index'       : '2',
                ]
                , Void.class)
        entity = postForEntity('/api/v1/test-plans',
                [
                    'service_uuid': DIY_DESCRIPTOR_TEST_PLAN_SERVICE_UUID,
                    'test_uuid'   : DIY_DESCRIPTOR_TEST_PLAN_TEST_UUID,
                    'nsd'        : DataMock.getService(DIY_DESCRIPTOR_TEST_PLAN_SERVICE_UUID).nsd,
                    'testd'      : DataMock.getTest(DIY_DESCRIPTOR_TEST_PLAN_TEST_UUID).testd,
                    'description': 'dummyTestPlan4-index4',
                    'index'      : '4',
                ]
                , Void.class)
        then:
        entity.statusCode == HttpStatus.OK
        def testPlans = testPlanService.testPlanRepository.findAll().findAll { it.status == "SCHEDULED" }
        testPlans[1].description == 'dummyTestPlan2-index3'
        testPlans[2].description == 'dummyTestPlan3-index2'
        cleanup:
        cleanTestPlanDB()
    }

    void "schedule request with validation required for one test plan should successfully schedule only the not validation required test plans"() {
        setup:
        curatorMock.isBusy(true)
        when:
        def entity = postForEntity('/api/v1/test-plans',
                [
                        'service_uuid': LATENCY_TEST_PLAN_SERVICE_UUID,
                        'test_uuid'   : LATENCY_TEST_PLAN_TEST_UUID,
                        'description' : 'dummyTestPlan1-non-validation_required',
                        'index'       : '1',
                ], Void.class)
        entity = postForEntity('/api/v1/test-plans',
                [
                        'service_uuid': DIY_DESCRIPTOR_TEST_PLAN_SERVICE_UUID,
                        'test_uuid'   : DIY_DESCRIPTOR_TEST_PLAN_VALIDATION_REQUIRED_TEST_UUID,
                        'description': 'dummyTestPlan-validation_required',
                        'index'      : '2',
                ], Void.class)
        entity = postForEntity('/api/v1/test-plans',
                [
                        'service_uuid': DIY_DESCRIPTOR_TEST_PLAN_SERVICE_UUID,
                        'test_uuid'   : DIY_DESCRIPTOR_TEST_PLAN_CONFIRMED_TEST_UUID,
                        'description': 'dummyTestPlan-validation_confirmed',
                        'index'      : '3',
                ], Void.class)
        then:
        entity.statusCode == HttpStatus.OK
        def testPlans = testPlanService.testPlanRepository.findAll().findAll { it.status != TEST_PLAN_STATUS.REJECTED }
        testPlans.get(0).status == TEST_PLAN_STATUS.SCHEDULED
        testPlans.get(0).description == 'dummyTestPlan1-non-validation_required'
        testPlans.get(1).status == TEST_PLAN_STATUS.NOT_CONFIRMED
        testPlans.get(1).description == 'dummyTestPlan-validation_required'
        testPlans.get(2).status == TEST_PLAN_STATUS.SCHEDULED
        testPlans.get(2).description == 'dummyTestPlan-validation_confirmed'
        cleanup:
        cleanTestPlanDB()
    }

    void "schedule request with validation required for one test plan should successfully schedule no test plans"() {
        setup:
        curatorMock.isBusy(false)
        when:
        def entity = postForEntity('/api/v1/test-plans',
            [
                    "service_uuid": DIY_DESCRIPTOR_TEST_PLAN_SERVICE_UUID,
                    "test_uuid": DIY_DESCRIPTOR_TEST_PLAN_VALIDATION_REQUIRED_TEST_UUID,
                    'description': 'dummyTestPlan1-validation_required',
                    'index': '1',
            ], Void.class)
        then:
        entity.statusCode == HttpStatus.OK
        testPlanService.testPlanRepository.findAll()
                .findAll{it.status == "SCHEDULED"}.size() == 0
        cleanup:
        cleanTestPlanDB()
    }

    void "schedule a non valid test.uuid OR non valid service.uuid OR a non existing test OR a non existing service should store only REJECTED testPlans"() {
        setup:
        curatorMock.isBusy(true)
        when:
        def entity = postForEntity('/api/v1/test-plans',
                [
                        "service_uuid": "",
                        "test_uuid": "",
                ],  Void.class)
        entity = postForEntity('/api/v1/test-plans',
                [
                        "service_uuid": LATENCY_TEST_PLAN_SERVICE_UUID,
                        "test_uuid": "",
                ],  Void.class)
        entity = postForEntity('/api/v1/test-plans',
                [
                        "service_uuid": "",
                        "test_uuid": LATENCY_TEST_PLAN_TEST_UUID,
                ],  Void.class)
        entity = postForEntity('/api/v1/test-plans',
                [
                        "service_uuid": UNKNOWN_UUID,
                        "test_uuid": LATENCY_TEST_PLAN_TEST_UUID,
                ],  Void.class)
        entity = postForEntity('/api/v1/test-plans',
                [
                        "service_uuid": LATENCY_TEST_PLAN_SERVICE_UUID,
                        "test_uuid": UNKNOWN_UUID,
                ],  Void.class)
        entity = postForEntity('/api/v1/test-plans',
                [
                        "service_uuid": UNKNOWN_UUID,
                        "test_uuid": UNKNOWN_UUID,
                ],  Void.class)
        then:
        entity.statusCode == HttpStatus.OK
        testPlanService.testPlanRepository.findAll()
                .findAll{it.status == "SCHEDULED"}.size() == 0
        cleanup:
        cleanTestPlanDB()
    }

    void "delete request for one test plan should successfully change the status of the test plan to CANCELING scheduled test plan"() {
        when:
        scheduleTestPlan(TEST_PLAN_UUID, TEST_PLAN_STATUS.CREATED, 'scheduled testPlan\'s status which will turn into canceling')
        and:
        delete('/api/v1/test-plans/{uuid}',TEST_PLAN_UUID)
        then:
        testPlanService.testPlanRepository.findByUuid(TEST_PLAN_UUID).status == TEST_PLAN_STATUS.CANCELLING
        cleanup:
        cleanTestPlanDB()
    }

    void "test plan request for one testPlan should successfully return the corresponding test plan"() {
        setup:
        curatorMock.isBusy(true)
        when:
        def testPlan = scheduleTestPlan(TEST_PLAN_UUID2, TEST_PLAN_STATUS.CREATED, 'retrieve a testPlan throught its uuid')
        and:
        def entity = getForEntity('/api/v1/test-plans/{uuid}', TestPlan, testPlan.uuid)
        then:
        entity.statusCode == HttpStatus.OK
        entity.body.description == 'retrieve a testPlan throught its uuid'
        entity.body.status == TEST_PLAN_STATUS.CREATED
        cleanup:
        cleanTestPlanDB()
    }

    void "request of all test plans should successfully return the list of all test plans"() {
        when:
        scheduleTestPlan(TEST_PLAN_UUID, "TEST_LIST_ALL_STATUS", '')
        and:
        def testPlans = getForEntity('/api/v1/test-plans/',TestPlan[]).body
        then:
        testPlans.size()>=1
        cleanup:
        cleanTestPlanDB()
    }
    
    void "create test plan from NetworkService"() {
        def testPlans = postForEntity('/api/v1/test-plans/services', ['uuid' : LATENCY_TEST_PLAN_SERVICE_UUID], List ).body
        testPlans.size()>=1
        cleanup:
        cleanTestPlanDB()
    }
    
    void "create test plan from Test"() {
        def testPlans = postForEntity('/api/v1/test-plans/tests', ['uuid' : TEST_DESCRIPTOR_UUID], List ).body
        testPlans.size()>=1
        cleanup:
        cleanTestPlanDB()
    }
    
    TestPlan scheduleTestPlan(String uuid, String status, String description){
        testPlanService.save(new TestPlan(uuid: uuid, status: status, description: description))
    }
}
