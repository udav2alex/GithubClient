package ru.geekbrains.githubclient.mvp.model.repo.retrofit;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.geekbrains.githubclient.mvp.model.api.IDataSource;
import ru.geekbrains.githubclient.mvp.model.entity.GithubUser;
import ru.geekbrains.githubclient.mvp.model.entity.UserRepository;
import ru.geekbrains.githubclient.mvp.model.repo.IGithubRepositoriesRepo;

public class RetrofitGithubRepositoriesRepo implements IGithubRepositoriesRepo {
    private final IDataSource dataSource;

    public RetrofitGithubRepositoriesRepo(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Single<List<UserRepository>> getRepos(GithubUser user) {
        return dataSource.getRepos(user.getReposUrl()).subscribeOn(Schedulers.io());
    }
}
