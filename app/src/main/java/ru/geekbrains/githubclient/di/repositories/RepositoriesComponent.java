package ru.geekbrains.githubclient.di.repositories;

import dagger.Subcomponent;
import ru.geekbrains.githubclient.di.repositories.module.RepositoriesModule;
import ru.geekbrains.githubclient.mvp.model.cache.room.RoomGithubRepositoriesCache;
import ru.geekbrains.githubclient.mvp.model.repo.retrofit.RetrofitGithubRepositoriesRepo;
import ru.geekbrains.githubclient.mvp.presenter.RepositoriesPresenter;
import ru.geekbrains.githubclient.mvp.presenter.ShowRepositoryPresenter;

@RepositoriesScope
@Subcomponent (
      modules = {
            RepositoriesModule.class
      }
)
public interface RepositoriesComponent {
      void inject(RepositoriesPresenter repositoriesPresenter);
      void inject(ShowRepositoryPresenter showRepositoryPresenter);
      void inject(RetrofitGithubRepositoriesRepo retrofitGithubRepositoriesRepo);
      void inject(RoomGithubRepositoriesCache roomGithubRepositoriesCache);
}
