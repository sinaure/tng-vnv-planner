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

package com.github.tng.vnv.planner.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable
import groovy.transform.ToString
import io.swagger.annotations.ApiModelProperty
import org.springframework.util.SerializationUtils

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.Lob
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.Transient
import javax.validation.constraints.NotNull

import static org.springframework.util.StringUtils.isEmpty

@Entity
@Table(name="Test_Plan")
@Sortable(includes = ['index'])
class TestPlan implements Serializable {
    @Id
    @GeneratedValue
    Long id

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "testSuiteId", referencedColumnName = "id", nullable = false)
    TestSuite testSuite

    @ApiModelProperty(
            value = 'Test Plan uuid',
            allowEmptyValue = false)
    String uuid
    @ApiModelProperty(
            value = 'Package id',
            allowEmptyValue = false)
    String packageId
    @ApiModelProperty(
            value = 'Network service uuid',
            allowEmptyValue = false)
    String serviceUuid
    @ApiModelProperty(
            value = 'Test uuid',
            allowEmptyValue = false)
    String testUuid
    @ApiModelProperty(
            value = 'Execution index',
            allowEmptyValue = false)
    int index
    @ApiModelProperty(
            value = 'Status',
            allowEmptyValue = false)
    String status
    @ApiModelProperty(
            value = 'Description',
            allowEmptyValue = true)
    String description
    @ApiModelProperty(
            value = 'Network service descriptor',
            allowEmptyValue = false)
    @Transient
    def nsd
    @ApiModelProperty(
            value = 'Test descriptor',
            allowEmptyValue = false)
    @Transient
    def testd

    @Lob
    @JsonIgnore
    @Column(name = "nsd", columnDefinition="OID")
    byte[] nsdJson

    @Lob
    @JsonIgnore
    @Column(name = "testd", columnDefinition="OID")
    byte[] testdJson


    TestPlan blob(){
        if(!(isEmpty(nsd)||isEmpty(testd))) {
            def mapper = new ObjectMapper()
            nsdJson = mapper.writeValueAsString(nsd).bytes
            testdJson = mapper.writeValueAsString(testd).bytes
        }
        this
    }
    TestPlan unBlob(){
        if(!(isEmpty(nsdJson) || isEmpty(testdJson))) {
            def mapper = new ObjectMapper()
            nsd = mapper.readValue(new String(nsdJson), HashMap.class)
            testd = mapper.readValue(new String(testdJson), HashMap.class)
        }
        this
    }

    boolean equals(o) {
        (o.uuid).contains(uuid)? true:false
    }

    int hashCode() {
        uuid.hashCode()
    }
}

class BlobOfLinkedHashMap extends LinkedHashMap implements Serializable {}

@EqualsAndHashCode
class TestPlanRequest {
    def nsd
    def testd
    String testPlanUuid
    Boolean lastTest = false
    List<TestPlanCallback> testPlanCallbacks = [
            new TestPlanCallback(eventActor: 'Curator', url: '/api/v1/test-plans/on-change/completed/', status:'COMPLETED'),
            new TestPlanCallback(eventActor: 'Curator', url: '/api/v1/test-plans/on-change/'),
    ]
}

@EqualsAndHashCode
class TestPlanResponse {
    @ApiModelProperty(
            value = 'Test Plan Status',
            allowEmptyValue = false,
            example = 'STARTING, COMPLETED, CANCELLING, CANCELLED, ERROR')
    String status

    @ApiModelProperty(
            value = 'Test Plan Exception message',
            allowEmptyValue = false,
            example = 'run time exception')
    String exception

}

class TestPlanCallback {

    @ApiModelProperty(
            value = 'Event Actor',
            allowEmptyValue = false,
            example = 'Curator, Executor',
            required = true)
    @NotNull
    String eventActor

    @ApiModelProperty(
            value = 'Test Plan Status',
            allowEmptyValue = false,
            example = 'STARTING, COMPLETED, CANCELLING, CANCELLED, ERROR',
            required = true)
    @NotNull
    String status

    @ApiModelProperty(
            value = 'Callback URL',
            allowEmptyValue = false,
            example = '/test-plans/on-change')
    String url

    @ApiModelProperty(
            value = 'Test Plan Result List',
            allowEmptyValue = true)
    List<TestResult> testResults

    @ApiModelProperty(
            value = 'Test Plan uuid',
            allowEmptyValue = false,
            required = true)
    @NotNull
    String testPlanUuid

    @ApiModelProperty(
            value = 'Test Plan Repository URI',
            allowEmptyValue = false,
            example = 'tng-cat, catalog, or xx.xx')
    String testPlanRepository

    @ApiModelProperty(
            value = 'Test Results Repository URI',
            allowEmptyValue = false,
            example = 'tng-res, results, or xx.xx')
    String testResultsRepository
}

class TestPlanTest{

    Map<String, Object> response = new ObjectMapper().readValue(str, HashMap.class)

    String objectMapper = new ObjectMapper().writeValueAsString(crunchifyMap)

}