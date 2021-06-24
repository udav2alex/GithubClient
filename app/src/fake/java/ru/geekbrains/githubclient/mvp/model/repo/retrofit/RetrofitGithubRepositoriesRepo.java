package ru.geekbrains.githubclient.mvp.model.repo.retrofit;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.geekbrains.githubclient.mvp.model.entity.GithubRepository;
import ru.geekbrains.githubclient.mvp.model.entity.GithubUser;
import ru.geekbrains.githubclient.mvp.model.repo.IGithubRepositoriesRepo;

public class RetrofitGithubRepositoriesRepo implements IGithubRepositoriesRepo {
    private final static List<GithubRepository> data = new ArrayList<>();

    static {
        for (int i = 0; i < 10; i++) {
            data.add(new GithubRepository(
                  "id" + i, "name " + i, "description " + i, i + 1
            ));
        }
    }

    @Override
    public Single<List<GithubRepository>> getRepos(GithubUser user) {
        return Single.just(data).subscribeOn(Schedulers.io());
    }
}
