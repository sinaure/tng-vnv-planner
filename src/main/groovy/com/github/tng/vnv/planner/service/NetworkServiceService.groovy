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

package com.github.tng.vnv.planner.service

import com.github.tng.vnv.planner.aspect.ServiceCall
import com.github.tng.vnv.planner.client.Catalogue
import com.github.tng.vnv.planner.client.Gatekeeper
import com.github.tng.vnv.planner.model.NetworkService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class NetworkServiceService {

    @Autowired
    Catalogue catalogue
    @Autowired
    Gatekeeper gatekeeper

    @ServiceCall
    NetworkService findByUuid(String uuid) {
        catalogue.getService(uuid).body
    }

    @ServiceCall
    List findByTest(def uuid){
        def matchedServices = [] as HashSet<NetworkService>
        def pack = gatekeeper.getPackageByTest(uuid).body
        if(pack != null){
            def testingTags = pack.pd.package_content.collect {it.testing_tags}
            testingTags?.each { tags ->
                tags?.each { tag ->
                    List packageList = gatekeeper.getPackageByTag(tag).body
                    packageList?.each {
                        it?.pd?.package_content.each { resource ->
                            if (resource.get('content-type')=='application/vnd.5gtango.nsd') {
                                matchedServices << findByUuid(resource.uuid)
                            }
                        }
                    }
                }
            }
        }
        new ArrayList(matchedServices)
    }

}
