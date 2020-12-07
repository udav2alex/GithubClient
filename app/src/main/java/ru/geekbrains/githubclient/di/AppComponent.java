package ru.geekbrains.githubclient.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.geekbrains.githubclient.MainActivity;
import ru.geekbrains.githubclient.di.module.ApiModule;
import ru.geekbrains.githubclient.di.module.ApplicationModule;
import ru.geekbrains.githubclient.di.module.CacheModule;
import ru.geekbrains.githubclient.di.module.CiceroneModule;
import ru.geekbrains.githubclient.di.users.UsersComponent;
import ru.geekbrains.githubclient.mvp.presenter.MainPresenter;
import ru.geekbrains.githubclient.ui.network.AndroidNetworkStatus;

@Singleton
@Component( modules = {
      ApiModule.class,
      ApplicationModule.class,
      CacheModule.class,
      CiceroneModule.class
})
public interface AppComponent {
    UsersComponent getUsersComponent();

    void inject(MainActivity mainActivity);
    void inject(MainPresenter mainPresenter);
    void inject(AndroidNetworkStatus androidNetworkStatus);
}
