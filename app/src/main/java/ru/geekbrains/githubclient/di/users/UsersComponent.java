package ru.geekbrains.githubclient.di.users;

import dagger.Subcomponent;
import ru.geekbrains.githubclient.di.repositories.RepositoriesComponent;
import ru.geekbrains.githubclient.di.users.module.UsersModule;
import ru.geekbrains.githubclient.mvp.model.cache.room.RoomGithubUsersCache;
import ru.geekbrains.githubclient.mvp.model.repo.retrofit.RetrofitGithubUsersRepo;
import ru.geekbrains.githubclient.mvp.presenter.UsersPresenter;

@UsersScope
@Subcomponent (
      modules = {
            UsersModule.class
      }
)
public interface UsersComponent {
      RepositoriesComponent getRepositoriesComponent();

      void inject(UsersPresenter usersPresenter);
      void inject(RetrofitGithubUsersRepo retrofitGithubUsersRepo);
      void inject(RoomGithubUsersCache roomGithubUsersCache);
}