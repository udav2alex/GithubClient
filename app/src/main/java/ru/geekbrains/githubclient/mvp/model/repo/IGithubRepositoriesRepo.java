package ru.geekbrains.githubclient.mvp.model.repo;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import ru.geekbrains.githubclient.mvp.model.entity.GithubUser;
import ru.geekbrains.githubclient.mvp.model.entity.GithubRepository;

public interface IGithubRepositoriesRepo {
    Single<List<GithubRepository>> getRepos(GithubUser user);
}
