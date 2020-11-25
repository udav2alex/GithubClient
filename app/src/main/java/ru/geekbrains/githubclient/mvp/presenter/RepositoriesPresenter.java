package ru.geekbrains.githubclient.mvp.presenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import moxy.MvpPresenter;
import ru.geekbrains.githubclient.GithubApplication;
import ru.geekbrains.githubclient.mvp.model.entity.GithubUser;
import ru.geekbrains.githubclient.mvp.model.entity.UserRepository;
import ru.geekbrains.githubclient.mvp.model.repo.IGithubUserRepo;
import ru.geekbrains.githubclient.mvp.model.repo.retrofit.RetrofitGithubUserRepo;
import ru.geekbrains.githubclient.mvp.presenter.list.IRepositoriesListPresenter;
import ru.geekbrains.githubclient.mvp.view.RepositoryItemView;
import ru.geekbrains.githubclient.mvp.view.RepositoriesView;
import ru.geekbrains.githubclient.navigation.Screens;
import ru.terrakok.cicerone.Router;

public class RepositoriesPresenter extends MvpPresenter<RepositoriesView> {
    private GithubUser githubUser;

    private final Router router = GithubApplication.getApplication().getRouter();

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final IGithubUserRepo userRepo;
    private final Scheduler scheduler;

    public RepositoriesPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
        this.userRepo = new RetrofitGithubUserRepo(
            GithubApplication.INSTANCE.getApi().getDataSource());
    }

    private class RepositoriesListPresenter implements IRepositoriesListPresenter {
        private final List<UserRepository> repositories = new ArrayList<>();

        @Override
        public void onItemClick(RepositoryItemView view) {
            router.navigateTo(
                new Screens.ShowRepositoryScreen(repositories.get(view.getPos())));
        }

        @Override
        public void bindView(RepositoryItemView view) {
            UserRepository repository = repositories.get(view.getPos());
            view.setName(repository.getName());
            view.setDescription(repository.getName());
        }

        @Override
        public int getCount() {
            return repositories.size();
        }
    }

    private final RepositoriesListPresenter repositoriesListPresenter = new RepositoriesListPresenter();

    public RepositoriesListPresenter getRepositoriesListPresenter() { return repositoriesListPresenter; }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().init();
        getViewState().setLogin(githubUser.getLogin());
        loadData();
    }

    private void loadData() {
        disposables.add(userRepo
            .getRepos(githubUser.getReposUrl())
            .observeOn(scheduler)
            .subscribe(
                (userRepositories) -> {
                    repositoriesListPresenter.repositories.clear();
                    repositoriesListPresenter.repositories.addAll(userRepositories);
                    getViewState().setReposCount(userRepositories.size());
                    getViewState().updateList();
                }
            ));
    }

    public void configure(GithubUser githubUser) {
        this.githubUser = githubUser;
    }

    public void onStop() {
        disposables.dispose();
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}
