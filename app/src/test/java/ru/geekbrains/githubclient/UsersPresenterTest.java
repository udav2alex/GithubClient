package ru.geekbrains.githubclient;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.geekbrains.githubclient.mvp.model.repo.IGithubUsersRepo;
import ru.geekbrains.githubclient.mvp.presenter.UsersPresenter;
import ru.terrakok.cicerone.Router;

import static org.junit.Assert.*;

public class UsersPresenterTest {

    private UsersPresenter presenter;

    private Scheduler scheduler = Schedulers.trampoline();

    @Mock
    IGithubUsersRepo userRepo;

    @Mock
    Router router;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        presenter = new UsersPresenter(scheduler, userRepo, router);
    }

    @Test
    public void navigateBack_Test() {
        presenter.backPressed();
        Mockito.verify(router, Mockito.times(1)).exit();
    }

    @Test
    public void loadData_Test() {

    }
}