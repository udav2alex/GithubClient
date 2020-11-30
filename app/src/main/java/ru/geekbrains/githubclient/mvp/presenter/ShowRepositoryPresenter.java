package ru.geekbrains.githubclient.mvp.presenter;

import moxy.MvpPresenter;
import ru.geekbrains.githubclient.GithubApplication;
import ru.geekbrains.githubclient.mvp.model.entity.GithubRepository;
import ru.geekbrains.githubclient.mvp.view.ShowRepositoryView;
import ru.terrakok.cicerone.Router;

public class ShowRepositoryPresenter extends MvpPresenter<ShowRepositoryView> {
    private GithubRepository githubRepository;

    private final Router router = GithubApplication.getApplication().getRouter();

    public void configure(GithubRepository githubRepository) {
        this.githubRepository = githubRepository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().setRepositoryName(githubRepository.getName());
        getViewState().setRepositoryDescription(githubRepository.getDescription());
        getViewState().setForkCount(githubRepository.getForks());
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}
