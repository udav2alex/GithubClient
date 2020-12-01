package ru.geekbrains.githubclient.mvp.model.repo.retrofit;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.geekbrains.githubclient.mvp.model.api.IDataSource;
import ru.geekbrains.githubclient.mvp.model.entity.GithubUser;
import ru.geekbrains.githubclient.mvp.model.repo.IGithubUsersRepo;

public class RetrofitGithubUsersRepo implements IGithubUsersRepo {
    private final IDataSource dataSource;

    public RetrofitGithubUsersRepo(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Single<List<GithubUser>> getUsers() {
        return dataSource.getUsers().subscribeOn(Schedulers.io());
    }
}
