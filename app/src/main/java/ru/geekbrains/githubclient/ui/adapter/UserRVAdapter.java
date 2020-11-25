package ru.geekbrains.githubclient.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.geekbrains.githubclient.R;
import ru.geekbrains.githubclient.mvp.presenter.list.IUserListPresenter;
import ru.geekbrains.githubclient.mvp.view.UserItemView;
import ru.geekbrains.githubclient.mvp.view.image.GlideImageLoader;
import ru.geekbrains.githubclient.mvp.view.image.IImageLoader;

public class UserRVAdapter extends RecyclerView.Adapter<UserRVAdapter.ViewHolder> {
    static IImageLoader<ImageView> imageLoader = new GlideImageLoader();
    IUserListPresenter presenter;

    public UserRVAdapter(IUserListPresenter presenter) {
       this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View userView = inflater.inflate(R.layout.item_user, parent, false);

        return new ViewHolder(userView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.position = position;

        holder.itemView.setOnClickListener(view -> presenter.onItemClick(holder));

        presenter.bindView(holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements UserItemView {
        TextView textView;
        ImageView imageView;
        int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_login);
            imageView = itemView.findViewById(R.id.iv_avatar);
        }

        @Override
        public void setLogin(String text) {
            textView.setText(text);
        }

        @Override
        public void setAvatar(String url) {
            imageLoader.loadImage(url, imageView);
        }

        @Override
        public int getPos() {
            return position;
        }
    }
}
