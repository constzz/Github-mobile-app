package com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.di

import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.data.repository.GitHubRepository
import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.data.source.GithubApi
import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.data.source.INetworkDataSource
import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.data.source.NetworkDataSource
import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.domain.usecase.GetGitHubRepoListUseCase
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.kodein.di.*
import kotlin.coroutines.CoroutineContext

val di = DI {

    //bind<CoroutineContext>() with provider { ApplicationDispatcher }

    bind<CoroutineContext>() with provider { Dispatchers.Main }

    bindSingleton<HttpClient> {
        HttpClient() {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
        }
    }

    /**
     * NETWORK API
     */
    bindProvider<GithubApi> { GithubApi() }

    /**
     * NETWORK DATA SOURCE
     */
    bindProvider<INetworkDataSource> { NetworkDataSource(instance(), instance()) }

    /**
     * REPOSITORIES
     */
    bindProvider<GitHubRepository> { GitHubRepository(instance()) }

    /**
     * USECASES
     */
    bindSingleton<GetGitHubRepoListUseCase> { GetGitHubRepoListUseCase(instance()) }
}