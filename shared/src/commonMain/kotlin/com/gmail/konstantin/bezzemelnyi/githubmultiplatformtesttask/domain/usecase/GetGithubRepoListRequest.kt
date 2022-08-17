package com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.domain.usecase

import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.domain.base.BaseRequest

class GetGitHubRepoListRequest(val username: String): BaseRequest {
    override fun validate(): Boolean {
        return true
    }
}