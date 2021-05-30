/*
 * Copyright (c) 2021 dzikoysk
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
 */

package org.panda_lang.reposilite.maven.repository.application

import io.javalin.Javalin
import org.panda_lang.reposilite.maven.repository.DeployService
import org.panda_lang.reposilite.maven.repository.RepositoryService
import org.panda_lang.reposilite.maven.repository.infrastructure.DeployEndpoint
import org.panda_lang.reposilite.web.ReposiliteContextFactory

object RepositoryWebConfiguration {

    fun createFacade(): RepositoryService {
        return RepositoryService()
    }

    fun installRouting(javalin: Javalin, contextFactory: ReposiliteContextFactory, deployService: DeployService) {
        val deployEndpoint = DeployEndpoint(contextFactory, deployService)

        javalin
            .get("/api", lookupApiEndpoint)
            .get("/api/*", lookupApiEndpoint)
            .get("/*", lookupController)
            .head("/*", lookupController)
            .put("/*", deployEndpoint)
            .post("/*", deployEndpoint);
    }

}