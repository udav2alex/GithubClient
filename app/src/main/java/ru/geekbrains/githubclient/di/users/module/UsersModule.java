package ru.geekbrains.githubclient.di.users.module;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.githubclient.di.users.UsersScope;
import ru.geekbrains.githubclient.mvp.model.cache.IGithubUsersCache;
import ru.geekbrains.githubclient.mvp.model.cache.room.RoomGithubUsersCache;
import ru.geekbrains.githubclient.mvp.model.repo.IGithubUsersRepo;
import ru.geekbrains.githubclient.mvp.model.repo.retrofit.RetrofitGithubUsersRepo;

@Module
public class UsersModule {
    @Provides
    IGithubUsersCache getIGithubUsersCache() {
        return new RoomGithubUsersCache();
    }

    @UsersScope
    @Provides
    IGithubUsersRepo getIGithubUsersRepo() {
        return new RetrofitGithubUsersRepo();
    }
}
