import SwiftUI
import shared
import Combine

struct GithubListView: View {
    
    @ObservedObject private var viewModel: GithubListViewModel
    
    enum DetailsWebViewState {
        case showm(url: URL)
        case hidden
    }
    
    private var cancellables = Set<AnyCancellable>()
    
    init(viewModel: GithubListViewModel = Self.GithubListViewModel()) {
        self.viewModel = viewModel
    }

	var body: some View {
        
        switch viewModel.state {
            
        case .idle:
            Color.clear.onAppear(perform: {
                Task {
                    await viewModel.fetchRepositories(for: "apple")
                }
            })
        
        case .loading:
            ProgressView()
            
        case .success(let githubRepoList):
            List(githubRepoList, id: \.self) { repo in
                HStack {
                    Text("\(repo.name)")
                    Spacer()
                    Text("\(repo.numberOfStars)")
                    Image(systemName: "star.fill")
                }.onTapGesture { viewModel.onListItemTap(repo) }
            }
            
        case .showingDetailsView(let URLRequest, let title):
            NavigationView {
                VStack {
                    WebView(request: URLRequest)
                }
                .navigationBarTitle(Text(title))
                .navigationBarItems(leading: Button(
                    action: { viewModel.onWebViewBackNavigationButtonTap() }
                ) { Image(systemName: "chevron.left") })
            }
            
        case .error(let errorMessage):
            Text(errorMessage)
        }
    }
}

// MARK: - ViewModel
extension GithubListView {
    
    class GithubListViewModel: ObservableObject {
        
        enum State {
            case idle
            case loading
            case success([GitHubRepo])
            case error(_ errorMessage: String)
            case showingDetailsView(_ urlRequest: URLRequest, _ title: String)
        }
        
        @Published var state: State = .idle
        
        private let wrapped = shared.GithubListViewModel()
        
        init() {
            observeSharedViewModel(wrapped)
            self.state = .idle
        }
        
        deinit {
            wrapped.onCleared()
        }
        
        func fetchRepositories(for username: String) async {
            do { try await wrapped.getGithubRepoList(username: username) }
            catch { print(error) }
        }
        
        func onListItemTap(_ item: GitHubRepo) {
            self.state = .showingDetailsView(
                URLRequest(url: URL(string: item.htmlUrl)!),
                item.name
            )
        }
        
        func onWebViewBackNavigationButtonTap() {
            guard let viewModelState = wrapped.getGitHubRepoListLiveData.value else {
                return
            }
            self.syncStateAndPublishState(viewModelState)
        }
        
        private func observeSharedViewModel(_ sharedViewModel: shared.GithubListViewModel) {
            sharedViewModel.getGitHubRepoListLiveData.addObserver { [weak self] getGitHubRepoListState in
                
                guard let self = self,
                      let getGitHubRepoListState = getGitHubRepoListState
                else { return }
                
                self.syncStateAndPublishState(getGitHubRepoListState)
            }
        }
        
        
        private func syncStateAndPublishState(_ state: GetGitHubRepoListState) {
            if (state is SuccessGetGitHubRepoListState) {
                
                let successState = state as! SuccessGetGitHubRepoListState
                let response = (successState.response as! ResponseSuccess)
                let value = response.value as! [GitHubRepo]
                
                self.state = .success(value)

            } else if state is LoadingGetGitHubRepoListState {
                
                self.state = .loading

            } else if state is ErrorGetGitHubRepoListState {
                
                let errorState = state as! ErrorGetGitHubRepoListState
                let response = errorState.response
                
                self.state = .error(response.exception.message ?? "Unknown error")
                
            }
        }
    }

}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
        GithubListView()
	}
}
