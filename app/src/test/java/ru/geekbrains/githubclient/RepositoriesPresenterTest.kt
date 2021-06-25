package ru.geekbrains.githubclient

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.MockitoAnnotations
import ru.geekbrains.githubclient.mvp.model.entity.GithubRepository
import ru.geekbrains.githubclient.mvp.model.entity.GithubUser
import ru.geekbrains.githubclient.mvp.model.repo.IGithubRepositoriesRepo
import ru.geekbrains.githubclient.mvp.presenter.RepositoriesPresenter
import ru.geekbrains.githubclient.mvp.view.RepositoriesView
import ru.geekbrains.githubclient.mvp.view.RepositoryItemView
import ru.geekbrains.githubclient.navigation.Screens
import ru.terrakok.cicerone.Router
import java.util.*

class RepositoriesPresenterTest {

    private lateinit var presenter: RepositoriesPresenter

    @Mock
    lateinit var repositoriesRepo: IGithubRepositoriesRepo

    @Mock
    lateinit var router: Router

    @Mock
    lateinit var reposView: RepositoriesView

    @Mock
    lateinit var user: GithubUser

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        presenter = RepositoriesPresenter(
            Schedulers.trampoline(),
            repositoriesRepo,
            router
        )

        `when`<Single<List<GithubRepository>>>(repositoriesRepo.getRepos(any()))
            .thenReturn(
                Single.just(ArrayList())
            )
        `when`(user.login).thenReturn("login")
        presenter.configure(user)

        presenter.attachView(reposView)
    }

    @Test
    fun navigateBack_Test() {
        presenter.backPressed()
        Mockito.verify(router, Mockito.times(1)).exit()
    }

    @Test
    fun loadData_Test() {
        // Empty list loaded in setUp() while attachView(...)
        Assert.assertNotNull(presenter.repositoriesListPresenter.count)
        Assert.assertEquals(ArrayList<GithubRepository>(), presenter.repositoriesListPresenter.repositories)
        Assert.assertEquals(0, presenter.repositoriesListPresenter.count)
        Mockito.verify(reposView).updateList()
    }

    @Test
    fun loadData_notEmpty_Test() {
        val reposList = ArrayList<GithubRepository>()
        reposList.add(GithubRepository("id1", "repo1", "description1", 1))
        reposList.add(GithubRepository("id2", "repo2", "description2", 2))
        reposList.add(GithubRepository("id3", "repo3", "description3", 3))
        `when`<Single<List<GithubRepository>>>(repositoriesRepo.getRepos(any()))
            .thenReturn(Single.just(reposList))
        presenter.loadData()
        Assert.assertNotNull(presenter.repositoriesListPresenter.repositories)
        Assert.assertEquals(reposList, presenter.repositoriesListPresenter.repositories)
        Assert.assertEquals(3, presenter.repositoriesListPresenter.count)

        // First time empty list loaded in setUp() while attachView(...)
        Mockito.verify(reposView, Mockito.times(2)).updateList()
    }

    @Test
    fun loadData_throwError_Test() {
        `when`<Single<List<GithubRepository>>>(repositoriesRepo.getRepos(any()))
            .thenReturn(Single.error(Throwable("Test error")))
        presenter.loadData()
        Mockito.verify(reposView).showError("Test error")
    }

    @Test
    fun binding4UsersListPresenter_notEmpty_Test() {
        val view = Mockito.mock(RepositoryItemView::class.java)
        val reposList = ArrayList<GithubRepository>()
        reposList.add(GithubRepository("id1", "repo1", "description1", 1))
        reposList.add(GithubRepository("id2", "repo2", "description2", 2))
        reposList.add(GithubRepository("id3", "repo3", "description3", 3))

        `when`<Single<List<GithubRepository>>>(repositoriesRepo.getRepos(any()))
            .thenReturn(Single.just(reposList))
        presenter.loadData()

        for ((i, repo) in reposList.withIndex()) {
            `when`(view.pos).thenReturn(i)
            presenter.repositoriesListPresenter.bindView(view)
            Mockito.verify(view).setName(repo.name)
            Mockito.verify(view).setDescription(repo.description)
        }
    }

    @Test
    fun navigation4UsersListPresenter_notEmpty_Test() {
        val view = Mockito.mock(RepositoryItemView::class.java)
        val reposList = ArrayList<GithubRepository>()
        reposList.add(GithubRepository("id1", "repo1", "description1", 1))
        reposList.add(GithubRepository("id2", "repo2", "description2", 2))
        reposList.add(GithubRepository("id3", "repo3", "description3", 3))

        `when`<Single<List<GithubRepository>>>(repositoriesRepo.getRepos(any()))
            .thenReturn(Single.just(reposList))
        presenter.loadData()

        for ((i, repo) in reposList.withIndex()) {
            `when`(view.pos).thenReturn(i)
            presenter.repositoriesListPresenter.onItemClick(view)
            Mockito.verify(router).navigateTo(ArgumentMatchers.eq(Screens.ShowRepositoryScreen(repo)))
        }
    }
}