package ru.geekbrains.githubclient.mvp.model.entity.room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ru.geekbrains.githubclient.GithubApplication;
import ru.geekbrains.githubclient.mvp.model.entity.room.dao.GithubRepositoryDAO;
import ru.geekbrains.githubclient.mvp.model.entity.room.dao.GithubUserDAO;

@Database(
    entities = {RoomGithubUser.class, RoomGithubRepository.class},
    version = 1,
    exportSchema = false)
public abstract class GithubDatabase extends RoomDatabase {
    public static final String GITHUB_DATABASE_NAME = "github-client.db";

    public abstract GithubUserDAO githubUserDAO();
    public abstract GithubRepositoryDAO githubRepositoryDAO();
}
