package ru.geekbrains.githubclient.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class UserRepository implements Parcelable {
    @Expose private String id;
    @Expose private String name;
    @Expose private String description;

    public UserRepository(String id) {
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
    protected UserRepository(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
    }

    public static final Creator<UserRepository> CREATOR = new Creator<UserRepository>() {
        @Override
        public UserRepository createFromParcel(Parcel in) {
            return new UserRepository(in);
        }

        @Override
        public UserRepository[] newArray(int size) {
            return new UserRepository[size];
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
