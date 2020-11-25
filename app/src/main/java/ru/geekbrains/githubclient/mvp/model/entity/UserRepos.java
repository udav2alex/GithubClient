package ru.geekbrains.githubclient.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class UserRepos implements Parcelable {
    @Expose private String id;
    @Expose private String name;
    @Expose private String description;

    public UserRepos(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    // Parcelization code below
    protected UserRepos(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
    }

    public static final Creator<UserRepos> CREATOR = new Creator<UserRepos>() {
        @Override
        public UserRepos createFromParcel(Parcel in) {
            return new UserRepos(in);
        }

        @Override
        public UserRepos[] newArray(int size) {
            return new UserRepos[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
    }
}
