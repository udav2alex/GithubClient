package ru.geekbrains.githubclient.mvp.presenter;

import moxy.MvpPresenter;
import ru.geekbrains.githubclient.GithubApplication;
import ru.geekbrains.githubclient.mvp.view.ShowUserView;
import ru.terrakok.cicerone.Router;

public class ShowUserPresenter extends MvpPresenter<ShowUserView> {
    private String login;

    private Router router = GithubApplication.getApplication().getRouter();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().setLogin(login);
    }

    public void configure(String login) {
        this.login = login;
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}
