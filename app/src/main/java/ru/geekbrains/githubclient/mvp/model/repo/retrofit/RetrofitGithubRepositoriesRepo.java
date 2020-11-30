package ru.geekbrains.githubclient.mvp.model.repo.retrofit;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.geekbrains.githubclient.mvp.model.api.IDataSource;
import ru.geekbrains.githubclient.mvp.model.entity.GithubUser;
import ru.geekbrains.githubclient.mvp.model.entity.GithubRepository;
import ru.geekbrains.githubclient.mvp.model.entity.room.GithubDatabase;
import ru.geekbrains.githubclient.mvp.model.entity.room.RoomGithubRepository;
import ru.geekbrains.githubclient.mvp.model.entity.room.dao.GithubRepositoryDAO;
import ru.geekbrains.githubclient.mvp.model.network.INetworkStatus;
import ru.geekbrains.githubclient.mvp.model.repo.IGithubRepositoriesRepo;

public class RetrofitGithubRepositoriesRepo implements IGithubRepositoriesRepo {
    private final IDataSource dataSource;
    private final INetworkStatus networkStatus;
    private final GithubRepositoryDAO githubRepositoryDAO;

    public RetrofitGithubRepositoriesRepo(
        IDataSource dataSource, INetworkStatus networkStatus, GithubDatabase database) {
        this.dataSource = dataSource;
        this.networkStatus = networkStatus;
        this.githubRepositoryDAO = database.githubRepositoryDAO();
    }

    @Override
    public Single<List<GithubRepository>> getRepos(GithubUser user) {
        return networkStatus.isOnlineSingle().flatMap((isOnline) -> {
                if (isOnline) {
                    return dataSource.getRepos(user.getReposUrl()).doOnSuccess(
                        (repos) -> githubRepositoryDAO.insert(domainList2RoomList(user, repos)
                        )
                    );
                } else {
                    return Single.fromCallable(() ->
                        roomList2DomainList(githubRepositoryDAO.getAll())
                    );
                }
            }
        ).subscribeOn(Schedulers.io());
    }

    private List<GithubRepository> roomList2DomainList(List<RoomGithubRepository> roomRepos) {
        List<GithubRepository> repos = new ArrayList<>();

        for (RoomGithubRepository roomRepo : roomRepos) {
            repos.add(new GithubRepository(
                roomRepo.getId(), roomRepo.getName(),
                roomRepo.getDescription(), roomRepo.getForks()
            ));
        }

        return repos;
    }

    private List<RoomGithubRepository> domainList2RoomList(GithubUser user, List<GithubRepository> repos) {
        List<RoomGithubRepository> roomRepos = new ArrayList<>();

        for (GithubRepository repo : repos) {
            roomRepos.add(new RoomGithubRepository(
                repo.getId(), repo.getName(),
                repo.getDescription(), repo.getForks(), user.getId()
            ));
        }

        return roomRepos;
    }
}
