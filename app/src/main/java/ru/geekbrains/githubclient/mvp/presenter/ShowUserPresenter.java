package ru.geekbrains.githubclient.mvp.presenter;

import moxy.MvpPresenter;
import ru.geekbrains.githubclient.GithubApplication;
import ru.geekbrains.githubclient.mvp.model.entity.GithubUser;
import ru.geekbrains.githubclient.mvp.view.ShowUserView;
import ru.terrakok.cicerone.Router;

public class ShowUserPresenter extends MvpPresenter<ShowUserView> {
    private GithubUser githubUser;

    private Router router = GithubApplication.getApplication().getRouter();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().setLogin(githubUser.getLogin());
    }

    public void configure(GithubUser githubUser) {
        this.githubUser = githubUser;
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}
