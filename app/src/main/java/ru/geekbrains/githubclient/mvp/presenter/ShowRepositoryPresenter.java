package ru.geekbrains.githubclient.mvp.presenter;

import moxy.MvpPresenter;
import ru.geekbrains.githubclient.GithubApplication;
import ru.geekbrains.githubclient.mvp.model.entity.UserRepository;
import ru.geekbrains.githubclient.mvp.view.ShowRepositoryView;
import ru.terrakok.cicerone.Router;

public class ShowRepositoryPresenter extends MvpPresenter<ShowRepositoryView> {
    private UserRepository userRepository;

    private final Router router = GithubApplication.getApplication().getRouter();

    public void configure(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().setRepositoryName(userRepository.getName());
        getViewState().setRepositoryDescription(userRepository.getDescription());
        getViewState().setForkCount(userRepository.getForks());
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}
