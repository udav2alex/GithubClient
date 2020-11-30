package ru.geekbrains.githubclient.mvp.model.repo.retrofit;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.geekbrains.githubclient.mvp.model.api.IDataSource;
import ru.geekbrains.githubclient.mvp.model.cache.IGithubRepositoriesCache;
import ru.geekbrains.githubclient.mvp.model.entity.GithubRepository;
import ru.geekbrains.githubclient.mvp.model.entity.GithubUser;
import ru.geekbrains.githubclient.mvp.model.network.INetworkStatus;
import ru.geekbrains.githubclient.mvp.model.repo.IGithubRepositoriesRepo;

public class RetrofitGithubRepositoriesRepo implements IGithubRepositoriesRepo {
    private final IDataSource dataSource;
    private final INetworkStatus networkStatus;
    private final IGithubRepositoriesCache repositoriesCache;

    public RetrofitGithubRepositoriesRepo(IDataSource dataSource,
                                          INetworkStatus networkStatus,
                                          IGithubRepositoriesCache repositoriesCache) {
        this.dataSource = dataSource;
        this.networkStatus = networkStatus;
        this.repositoriesCache = repositoriesCache;
    }

    @Override
    public Single<List<GithubRepository>> getRepos(GithubUser user) {
        return networkStatus.isOnlineSingle().flatMap((isOnline) -> {
                  if (isOnline) {
                      return dataSource.getRepos(user.getReposUrl()).doOnSuccess(
                            repositories -> repositoriesCache.saveRepositories(repositories, user));
                  } else {
                      return repositoriesCache.loadRepositories(user);
                  }
              }
        ).subscribeOn(Schedulers.io());
    }
}
