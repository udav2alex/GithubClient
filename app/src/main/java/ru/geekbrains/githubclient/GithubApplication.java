package ru.geekbrains.githubclient;

import android.app.Application;
import android.content.Context;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class GithubApplication extends Application {
    public static GithubApplication INSTANCE;

    private Cicerone<Router> cicerone;

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;

        initCicerone();
    }

    public static GithubApplication getApplication() {
        return INSTANCE;
    }

    private void initCicerone() {
        cicerone = Cicerone.create();
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }
}
