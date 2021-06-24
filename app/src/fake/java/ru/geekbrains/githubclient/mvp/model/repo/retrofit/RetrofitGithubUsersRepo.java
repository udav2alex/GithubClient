package ru.geekbrains.githubclient.mvp.model.repo.retrofit;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.geekbrains.githubclient.mvp.model.entity.GithubUser;
import ru.geekbrains.githubclient.mvp.model.repo.IGithubUsersRepo;

public class RetrofitGithubUsersRepo implements IGithubUsersRepo {
    private static final List<GithubUser> data = new ArrayList<>();
    static {
        for (int i = 0; i < 20; i++) {
            data.add(new GithubUser("id" + i, "login" + i, "", ""));
        }
    }

    @Override
    public Single<List<GithubUser>> getUsers() {
        return Single.just(data).subscribeOn(Schedulers.io());
    }
}
