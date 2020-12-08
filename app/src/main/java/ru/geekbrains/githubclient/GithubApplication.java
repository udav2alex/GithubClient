package ru.geekbrains.githubclient;

import android.app.Application;

import ru.geekbrains.githubclient.di.AppComponent;
import ru.geekbrains.githubclient.di.DaggerAppComponent;
import ru.geekbrains.githubclient.di.module.ApplicationModule;
import ru.geekbrains.githubclient.di.repositories.RepositoriesComponent;
import ru.geekbrains.githubclient.di.users.UsersComponent;

public class GithubApplication extends Application {
    static GithubApplication instance;

    AppComponent appComponent;
    UsersComponent usersComponent = null;
    RepositoriesComponent repositoriesComponent = null;

    public static final boolean DEBUG_MODE = true;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        appComponent = DaggerAppComponent.builder()
              .applicationModule(new ApplicationModule(this)).build();
    }

    public static GithubApplication getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public UsersComponent getUsersComponent() {
        if (usersComponent == null) {
            usersComponent = appComponent.getUsersComponent();
        }
        return usersComponent;
    }

    public RepositoriesComponent getRepositoriesComponent() {
        if (repositoriesComponent == null) {
            repositoriesComponent = appComponent.getUsersComponent().getRepositoriesComponent();
        }
        return repositoriesComponent;
    }

    public void releaseUsersComponent() {
        usersComponent = null;
    }

    public void releaseRepositoriesComponent() {
        repositoriesComponent = null;
    }
}
