package com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.data.source

import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.model.GitHubRepo
import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.base.Response
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class GithubApi(){
    suspend fun getGithubRepoList(networkClient: HttpClient, userName: String): Response<List<GitHubRepo>> {
        val url = "https://api.github.com/orgs/${userName}/repos"
        val response: List<GitHubRepo> = networkClient.get(url).body()

        return Response.Success(response)
    }
}