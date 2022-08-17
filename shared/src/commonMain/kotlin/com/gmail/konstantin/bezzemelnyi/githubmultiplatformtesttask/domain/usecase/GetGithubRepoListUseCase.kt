package com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.domain.usecase

import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.base.Response
import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.data.repository.GitHubRepository
import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.domain.base.BaseUseCase
import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.model.GitHubRepo

open class GetGitHubRepoListUseCase(
    val repository: GitHubRepository
) : BaseUseCase<GetGitHubRepoListRequest, List<GitHubRepo>>() {

    override suspend fun run(): Response<List<GitHubRepo>> {
        return repository.getRepos(request!!)
    }
}