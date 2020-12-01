package ru.geekbrains.githubclient.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class GithubUser implements Parcelable {
    @Expose private String login;
    @Expose private String id;
    @Expose private String avatarUrl;
    @Expose private String reposUrl;

    public GithubUser(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public String getId() {
        return id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getReposUrl() {
        return reposUrl;
    }


    // Parcelization code below
    protected GithubUser(Parcel in) {
        login = in.readString();
        id = in.readString();
        avatarUrl = in.readString();
        reposUrl = in.readString();

    }

    public static final Creator<GithubUser> CREATOR = new Creator<GithubUser>() {
        @Override
        public GithubUser createFromParcel(Parcel in) {
            return new GithubUser(in);
        }

        @Override
        public GithubUser[] newArray(int size) {
            return new GithubUser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(login);
        dest.writeString(id);
        dest.writeString(avatarUrl);
        dest.writeString(reposUrl);
    }
}
