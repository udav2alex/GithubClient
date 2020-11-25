package ru.geekbrains.githubclient.mvp.model.repo.retrofit;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.geekbrains.githubclient.mvp.model.api.IDataSource;
import ru.geekbrains.githubclient.mvp.model.entity.GithubUser;
import ru.geekbrains.githubclient.mvp.model.entity.UserRepository;
import ru.geekbrains.githubclient.mvp.model.repo.IGithubUserRepo;

public class RetrofitGithubUserRepo implements IGithubUserRepo {
    private final IDataSource dataSource;

    public RetrofitGithubUserRepo(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Single<List<GithubUser>> getUsers() {
        return dataSource.getUsers().subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<UserRepository>> getRepos(String reposUrl) {
        return dataSource.getRepos(reposUrl).subscribeOn(Schedulers.io());
    }
}
