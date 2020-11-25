package ru.geekbrains.githubclient.mvp.presenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import moxy.MvpPresenter;
import ru.geekbrains.githubclient.GithubApplication;
import ru.geekbrains.githubclient.mvp.model.entity.GithubUser;
import ru.geekbrains.githubclient.mvp.model.repo.IGithubUserRepo;
import ru.geekbrains.githubclient.mvp.model.repo.retrofit.RetrofitGithubUserRepo;
import ru.geekbrains.githubclient.mvp.presenter.list.IUserListPresenter;
import ru.geekbrains.githubclient.mvp.view.UserItemView;
import ru.geekbrains.githubclient.mvp.view.UsersView;
import ru.geekbrains.githubclient.navigation.Screens;
import ru.terrakok.cicerone.Router;

public class UsersPresenter extends MvpPresenter<UsersView> {
    private final Router router = GithubApplication.getApplication().getRouter();

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final IGithubUserRepo userRepo;
    private final Scheduler scheduler;

    public UsersPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
        this.userRepo = new RetrofitGithubUserRepo(
            GithubApplication.INSTANCE.getApi().getDataSource());
    }

    private class UsersListPresenter implements IUserListPresenter {

        private final List<GithubUser> users = new ArrayList<>();

        @Override
        public void onItemClick(UserItemView view) {
            router.navigateTo(
                new Screens.ViewUserScreen(users.get(view.getPos())));
        }

        @Override
        public void bindView(UserItemView view) {
            GithubUser user = users.get(view.getPos());
            view.setLogin(user.getLogin());
            view.setAvatar(user.getAvatarUrl());
        }

        @Override
        public int getCount() {
            return users.size();
        }
    }

    private final UsersListPresenter usersListPresenter = new UsersListPresenter();

    public UsersListPresenter getUsersListPresenter() {
        return usersListPresenter;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().init();
        loadData();

    }

    private void loadData() {
        disposables.add(userRepo.getUsers()
            .observeOn(scheduler)
            .subscribe(
                (userList) -> {
                    usersListPresenter.users.clear();
                    usersListPresenter.users.addAll(userList);
                    getViewState().updateList();
                },
                (throwable) -> getViewState().showError(throwable.getMessage())
            ));
    }

    public void onStop() {
        disposables.clear();
    }

    public boolean backPressed() {
        router.exit();
        return true;

    }
}
