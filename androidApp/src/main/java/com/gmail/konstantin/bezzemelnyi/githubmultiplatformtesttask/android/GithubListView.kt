package com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.android

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.model.GitHubRepo
import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.viewmodel.ErrorGetGitHubRepoListState
import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.viewmodel.LoadingGetGitHubRepoListState
import com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.viewmodel.SuccessGetGitHubRepoListState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
typealias SharedGithubListViewModel = com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.viewmodel.GithubListViewModel

@OptIn(ExperimentalCoroutinesApi::class)
@Preview
@Composable
fun GithubListView(viewModel: GithubListViewModel = GithubListViewModel()) {
    Scaffold(modifier = Modifier.background(Color.Red)) { padding ->
        when (val state = viewModel.state.observeAsState().value) {
            is GithubListViewModel.State.Idle -> {
                Box(
                    Modifier
                        .background(Color.White)
                        .padding(padding)
                )
            }

            is GithubListViewModel.State.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is GithubListViewModel.State.Success -> {
                LazyColumn(
                    Modifier
                        .padding(padding)
                        .padding(horizontal = 16.dp)) {
                    items(state.list) { repo ->
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .alpha(0.0f)
                        )
                        Row(
                            Modifier.height(40.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(repo.name, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.weight(1.0F))
                            Text(repo.numberOfStars.toString(), fontSize = 14.sp)
                            Icon(Icons.Rounded.Star, contentDescription = "Localized description")
                        }
                        Divider(
                            color = Color.LightGray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                        )
                    }
                }
            }


            is GithubListViewModel.State.Error -> {
                Text(state.errorMessage, modifier = Modifier.padding(padding))
            }

            null -> {
                throw Error("STATE IS NULL")
            }
        }

    }

}

@OptIn(ExperimentalCoroutinesApi::class)
class GithubListViewModel constructor(
    private val wrapped: SharedGithubListViewModel = SharedGithubListViewModel()
) : ViewModel() {

    sealed class State {
        object Idle : State()
        object Loading : State()
        data class Success(val list: List<GitHubRepo>) : State()
        data class Error(val errorMessage: String) : State()
    }

    var state = MutableLiveData<State>(State.Loading)

    init {
        observeViewModel(this.wrapped)
        fetchRepositories(username = "apple")
    }

    private fun fetchRepositories(username: String) {
        viewModelScope.launch {
            wrapped.getGithubRepoList(username)
        }
    }


    private fun observeViewModel(viewModel: SharedGithubListViewModel): Unit {
        viewModel.getGitHubRepoListLiveData.addObserver {
            when (it) {
                is SuccessGetGitHubRepoListState -> {
                    this.state.postValue(State.Success(it.response.value))
                }
                is LoadingGetGitHubRepoListState -> {
                    this.state.postValue(State.Loading)
                }
                is ErrorGetGitHubRepoListState -> {
                    this.state.postValue(State.Error(it.response.message ?: "Unknown error"))
                }
            }
        }
    }

}