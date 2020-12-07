package ru.geekbrains.githubclient.di.repositories.module;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.githubclient.di.repositories.RepositoriesScope;
import ru.geekbrains.githubclient.mvp.model.cache.IGithubRepositoriesCache;
import ru.geekbrains.githubclient.mvp.model.cache.room.RoomGithubRepositoriesCache;
import ru.geekbrains.githubclient.mvp.model.repo.IGithubRepositoriesRepo;
import ru.geekbrains.githubclient.mvp.model.repo.retrofit.RetrofitGithubRepositoriesRepo;

@Module
public class RepositoriesModule {
    @Provides
    IGithubRepositoriesCache getIGithubRepositoriesCache() {
        return new RoomGithubRepositoriesCache();
    }

    @RepositoriesScope
    @Provides
    IGithubRepositoriesRepo getIGithubRepositoriesRepo() {
        return new RetrofitGithubRepositoriesRepo();
    }
}
