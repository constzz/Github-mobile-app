package com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.data.repository

import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.base.Response
import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.data.source.INetworkDataSource
import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.domain.usecase.GetGitHubRepoListRequest
import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.model.GitHubRepo

class GitHubRepository(
    private val networkDataSource: INetworkDataSource
) {
    suspend fun getRepos(request: GetGitHubRepoListRequest): Response<List<GitHubRepo>> {
        return networkDataSource.getGitHubRepoList(request.username)
    }
}
