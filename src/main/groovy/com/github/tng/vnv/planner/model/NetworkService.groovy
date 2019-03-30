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

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import io.swagger.annotations.ApiModelProperty

import javax.validation.constraints.NotNull

class NetworkService {
    @ApiModelProperty(required = true)
    @NotNull
    String uuid
    String status
    String packageId
    def nsd
    NetworkServiceDescriptor descriptor

    NetworkService reload() {
        descriptor = new NetworkServiceDescriptor()
        descriptor.load(this)
        this
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false
        NetworkService that = (NetworkService) o
        if (uuid != that.uuid) return false
        return true
    }

    int hashCode() {
        return uuid.hashCode()
    }
}

@ToString(excludes = ['name','vendor'])
class NetworkServiceDescriptor implements Serializable {
    String uuid
    String name
    String vendor
    String version
    List<String> testingTags
    List<String> servicePlatforms

    void load(NetworkService service){
        uuid = service.nsd.uuid
        name = service.nsd.name
        vendor = service.nsd.vendor
        testingTags = service.nsd.testing_tags
        servicePlatforms = service.nsd.service_platforms
    }
    boolean tagMatchedWith(TestDescriptor testDescriptor) {
        (testDescriptor == null)?false:this?.testingTags?.flatten().any { st -> testDescriptor?.testTags?.flatten().any{ tt -> tt == st}}
    }
}
