package ru.geekbrains.githubclient;

import android.app.Application;
import android.content.Context;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class GithubApplication extends Application {
    public static GithubApplication INSTANCE;

    public static final boolean DEBUG_MODE = true;

    private Cicerone<Router> cicerone;
    private ApiHolder apiHolder;

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;

        initCicerone();
    }

    public static GithubApplication getApplication() {
        return INSTANCE;
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }

    public ApiHolder getApi() {
        return apiHolder;
    }

    private void initCicerone() {
        cicerone = Cicerone.create();
        apiHolder = new ApiHolder();
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }
}
