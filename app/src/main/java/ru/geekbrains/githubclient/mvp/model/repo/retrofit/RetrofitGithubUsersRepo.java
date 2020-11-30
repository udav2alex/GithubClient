package ru.geekbrains.githubclient.mvp.model.repo.retrofit;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.geekbrains.githubclient.mvp.model.api.IDataSource;
import ru.geekbrains.githubclient.mvp.model.entity.GithubUser;
import ru.geekbrains.githubclient.mvp.model.entity.room.GithubDatabase;
import ru.geekbrains.githubclient.mvp.model.entity.room.RoomGithubUser;
import ru.geekbrains.githubclient.mvp.model.entity.room.dao.GithubUserDAO;
import ru.geekbrains.githubclient.mvp.model.network.INetworkStatus;
import ru.geekbrains.githubclient.mvp.model.repo.IGithubUsersRepo;

public class RetrofitGithubUsersRepo implements IGithubUsersRepo {
    private final IDataSource dataSource;
    private final INetworkStatus networkStatus;
    private final GithubUserDAO githubUserDAO;

    public RetrofitGithubUsersRepo(IDataSource dataSource, INetworkStatus networkStatus, GithubDatabase db) {
        this.dataSource = dataSource;
        this.networkStatus = networkStatus;
        this.githubUserDAO = db.githubUserDAO();
    }

    @Override
    public Single<List<GithubUser>> getUsers() {
        return networkStatus.isOnlineSingle().flatMap(isOnline -> {
            if (isOnline) {
                return dataSource.getUsers().doOnSuccess(
                    (users) -> githubUserDAO.insert(domainList2RoomList(users))
                );
            } else {
                return Single.fromCallable(
                    () -> roomList2DomainList(githubUserDAO.getAll())
                );
            }
        }).subscribeOn(Schedulers.io());
    }

    private List<RoomGithubUser> domainList2RoomList(List<GithubUser> users) {
        List<RoomGithubUser> roomUsers = new ArrayList<>();

        for (GithubUser user : users) {
            roomUsers.add(new RoomGithubUser(
                user.getId(),
                user.getLogin(),
                user.getAvatarUrl(),
                user.getReposUrl()));
        }

        return roomUsers;
    }

    private List<GithubUser> roomList2DomainList(List<RoomGithubUser> roomUsers) {
        List<GithubUser> users = new ArrayList<>();

        for (RoomGithubUser roomGithubUser : roomUsers) {
            users.add(new GithubUser(
                roomGithubUser.getId(),
                roomGithubUser.getLogin(),
                roomGithubUser.getAvatarUrl(),
                roomGithubUser.getReposUrl()));
        }

        return users;
    }
}
