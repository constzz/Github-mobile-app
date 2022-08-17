package com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.data.source

import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.model.GitHubRepo
import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.base.Response

interface INetworkDataSource {
    suspend fun getGitHubRepoList(userName: String): Response<List<GitHubRepo>>
}