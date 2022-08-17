package com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.viewmodel

import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.base.Response
import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.model.GitHubRepo

sealed class GetGitHubRepoListState {
    abstract val response: Response<List<GitHubRepo>>?
}

data class SuccessGetGitHubRepoListState(override val response: Response<List<GitHubRepo>>) : GetGitHubRepoListState()
data class LoadingGetGitHubRepoListState(override val response: Response<List<GitHubRepo>>? = null) : GetGitHubRepoListState()
data class ErrorGetGitHubRepoListState(override val response: Response<List<GitHubRepo>>) : GetGitHubRepoListState()
