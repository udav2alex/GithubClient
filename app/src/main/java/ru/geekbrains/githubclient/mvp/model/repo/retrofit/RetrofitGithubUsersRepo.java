package ru.geekbrains.githubclient.mvp.model.repo.retrofit;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.geekbrains.githubclient.mvp.model.api.IDataSource;
import ru.geekbrains.githubclient.mvp.model.cache.IGithubUserCache;
import ru.geekbrains.githubclient.mvp.model.entity.GithubUser;
import ru.geekbrains.githubclient.mvp.model.network.INetworkStatus;
import ru.geekbrains.githubclient.mvp.model.repo.IGithubUsersRepo;

public class RetrofitGithubUsersRepo implements IGithubUsersRepo {
    private final IDataSource dataSource;
    private final INetworkStatus networkStatus;
    private final IGithubUserCache userCache;

    public RetrofitGithubUsersRepo(
          IDataSource dataSource, INetworkStatus networkStatus, IGithubUserCache userCache) {
        this.dataSource = dataSource;
        this.networkStatus = networkStatus;
        this.userCache = userCache;
    }

    @Override
    public Single<List<GithubUser>> getUsers() {
        return networkStatus.isOnlineSingle().flatMap(isOnline -> {
            if (isOnline) {
                return dataSource.getUsers().doOnSuccess(userCache::saveUsers);
            } else {
                return userCache.loadUsers();
            }
        }).subscribeOn(Schedulers.io());
    }
}
