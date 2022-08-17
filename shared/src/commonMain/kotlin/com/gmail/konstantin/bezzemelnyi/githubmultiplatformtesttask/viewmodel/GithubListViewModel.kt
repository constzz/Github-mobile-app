package com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.viewmodel

import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.base.Response
import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.di.di
import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.domain.usecase.GetGitHubRepoListRequest
import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.domain.usecase.GetGitHubRepoListUseCase
import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.model.GitHubRepo
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.postValue
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch
import org.kodein.di.instance

class GithubListViewModel : ViewModel() {

    private val mGetGitHubRepoListUseCase by di.instance<GetGitHubRepoListUseCase>()

    val getGitHubRepoListLiveData = MutableLiveData<GetGitHubRepoListState>(LoadingGetGitHubRepoListState())

    fun getGithubRepoList(username: String) {
        viewModelScope.launch {

            getGitHubRepoListLiveData.postValue(LoadingGetGitHubRepoListState())


            val request = GetGitHubRepoListRequest(username)
            val response = mGetGitHubRepoListUseCase.repository.getRepos(request)

            processGitHubRepoListResponse(response)
        }
    }

    private fun processGitHubRepoListResponse(response: Response<List<GitHubRepo>>) {
        when (response) {
            is Response.Success -> getGitHubRepoListLiveData.postValue(SuccessGetGitHubRepoListState(response))
            is Response.Error -> getGitHubRepoListLiveData.postValue(ErrorGetGitHubRepoListState(response))
        }
    }

}