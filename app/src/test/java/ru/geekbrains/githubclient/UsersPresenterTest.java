package ru.geekbrains.githubclient;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.geekbrains.githubclient.mvp.model.entity.GithubUser;
import ru.geekbrains.githubclient.mvp.model.repo.IGithubUsersRepo;
import ru.geekbrains.githubclient.mvp.presenter.UsersPresenter;
import ru.geekbrains.githubclient.mvp.view.UserItemView;
import ru.geekbrains.githubclient.mvp.view.UsersView;
import ru.geekbrains.githubclient.navigation.Screens;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.Screen;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UsersPresenterTest {

    private UsersPresenter presenter;

    private final Scheduler scheduler = Schedulers.trampoline();

    @Mock
    IGithubUsersRepo userRepo;

    @Mock
    Router router;

    @Mock
    UsersView usersView;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        presenter = new UsersPresenter(scheduler, userRepo, router);

        when(userRepo.getUsers())
              .thenReturn(
                    Single.just(new ArrayList<>())
              );

        presenter.attachView(usersView);
    }

    @Test
    public void navigateBack_Test() {
        presenter.backPressed();
        verify(router, Mockito.times(1)).exit();
    }

    @Test
    public void loadData_Test() {
        // Empty list loaded in setUp() while attachView(...)

        assertNotNull(presenter.getUsersListPresenter().users);
        assertEquals(new ArrayList<GithubUser>(), presenter.getUsersListPresenter().users);
        assertEquals(0, presenter.getUsersListPresenter().getCount());
        verify(usersView).updateList();
    }

    @Test
    public void loadData_notEmpty_Test() {
        ArrayList<GithubUser> usersList = new ArrayList<>();
        usersList.add(new GithubUser("id1", "login1", "avatarUrl1", "reposUrl1"));
        usersList.add(new GithubUser("id2", "login2", "avatarUrl2", "reposUrl2"));
        usersList.add(new GithubUser("id3", "login3", "avatarUrl3", "reposUrl3"));
        when(userRepo.getUsers()).thenReturn(Single.just(usersList));

        presenter.loadData();

        assertNotNull(presenter.getUsersListPresenter().users);
        assertEquals(usersList, presenter.getUsersListPresenter().users);
        assertEquals(3, presenter.getUsersListPresenter().getCount());

        // First time empty list loaded in setUp() while attachView(...)
        verify(usersView, times(2)).updateList();
    }

    @Test
    public void loadData_throwError_Test() {
        when(userRepo.getUsers()).thenReturn(Single.error(new Throwable("Test error")));

        presenter.loadData();

        verify(usersView).showError("Test error");
    }

    @Test
    public void binding4UsersListPresenter_notEmpty_Test() {
        UserItemView view = mock(UserItemView.class);

        ArrayList<GithubUser> usersList = new ArrayList<>();
        usersList.add(new GithubUser("id1", "login1", "avatarUrl1", "reposUrl1"));
        usersList.add(new GithubUser("id2", "login2", "avatarUrl2", "reposUrl2"));
        usersList.add(new GithubUser("id3", "login3", "avatarUrl3", "reposUrl3"));
        when(userRepo.getUsers()).thenReturn(Single.just(usersList));

        presenter.loadData();

        int i = 0;
        for (GithubUser user : usersList) {
            when(view.getPos()).thenReturn(i++);
            presenter.getUsersListPresenter().bindView(view);

            verify(view).setLogin(user.getLogin());
            verify(view).setAvatar(user.getAvatarUrl());
        }
    }

    @Test
    public void navigation4UsersListPresenter_notEmpty_Test() {
        UserItemView view = mock(UserItemView.class);

        ArrayList<GithubUser> usersList = new ArrayList<>();
        usersList.add(new GithubUser("id1", "login1", "avatarUrl1", "reposUrl1"));
        usersList.add(new GithubUser("id2", "login2", "avatarUrl2", "reposUrl2"));
        usersList.add(new GithubUser("id3", "login3", "avatarUrl3", "reposUrl3"));
        when(userRepo.getUsers()).thenReturn(Single.just(usersList));

        presenter.loadData();

        int i = 0;
        for (GithubUser user : usersList) {
            when(view.getPos()).thenReturn(i++);
            presenter.getUsersListPresenter().onItemClick(view);

            verify(router).navigateTo(eq(new Screens.RepositoriesScreen(user)));
        }
    }
}