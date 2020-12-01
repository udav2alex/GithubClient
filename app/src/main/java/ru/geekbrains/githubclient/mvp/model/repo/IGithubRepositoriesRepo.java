package ru.geekbrains.githubclient.mvp.model.repo;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import ru.geekbrains.githubclient.mvp.model.entity.GithubUser;
import ru.geekbrains.githubclient.mvp.model.entity.UserRepository;

public interface IGithubRepositoriesRepo {
    Single<List<UserRepository>> getRepos(GithubUser user);
}
