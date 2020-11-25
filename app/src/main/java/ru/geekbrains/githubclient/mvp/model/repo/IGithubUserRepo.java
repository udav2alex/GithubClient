package ru.geekbrains.githubclient.mvp.model.repo;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import ru.geekbrains.githubclient.mvp.model.entity.GithubUser;

public interface IGithubUserRepo {
    Single<List<GithubUser>> getUsers();
    Single<List<GithubUser>> getRepos(String userLogin);
}
