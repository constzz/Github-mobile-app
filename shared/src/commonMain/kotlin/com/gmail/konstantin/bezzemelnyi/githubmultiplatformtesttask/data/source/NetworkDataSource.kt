package com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.data.source

import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.model.GitHubRepo
import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.base.Response
import io.ktor.client.*

class NetworkDataSource(
    private val networkClient: HttpClient,
    private val githubApi: GithubApi
): INetworkDataSource {

    override suspend fun getGitHubRepoList(userName: String): Response<List<GitHubRepo>> {
        return githubApi.getGithubRepoList(networkClient = this.networkClient, userName = userName)
    }

}