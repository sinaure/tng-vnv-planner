/*
 * Copyright (c) 2015 SONATA-NFV, 2019 5GTANGO [, ANY ADDITIONAL AFFILIATION]
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

package com.github.tng.vnv.planner.restmock

import com.github.tng.vnv.planner.model.NetworkService
import groovy.json.JsonSlurper
import org.springframework.util.ResourceUtils

class DataMock {

    static def getTest(String uuid) {
        getTests().find{it.uuid == uuid}
    }

    static def getService(String uuid) {
        getServices().find{it.uuid == uuid}
    }

    static def getPackage(String uuid) {
        getPackages().find{it.uuid == uuid}
    }

    static def getTests(){
        attachJsonData("classpath:static/json/tests.json")
    }

    static def getServices(){
        attachJsonData("classpath:static/json/services.json")
    }

    static def getPackages() {
        attachJsonData("classpath:static/json/packages.json")
    }

    static def attachJsonData(String resourceLocation){
        File file = ResourceUtils.getFile(resourceLocation)
        (file.exists()) ? new JsonSlurper().parseText(file.text) :  null
    }

	static def getServiceByTag(String tag) {
        getServices().findAll { it -> it.testing_tags?.contains(tag)}
	}

	static def getTestByTag(String tag) {
        getTests().findAll {it -> it.testing_tags?.contains(tag)}
	}

    static def getPackageByTag(String tag){
        getPackages().findAll {it -> it.pd.package_content.testing_tags.join(", ").contains(tag)}
    }

    static def getPackageByUuid(uuid){
        getPackages().find {it -> it.pd.package_content.uuid?.contains(uuid)}

    }
}
