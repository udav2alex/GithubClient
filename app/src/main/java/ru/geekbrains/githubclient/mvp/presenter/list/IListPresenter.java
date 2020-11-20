package ru.geekbrains.githubclient.mvp.presenter.list;

import ru.geekbrains.githubclient.mvp.view.IItemView;

public interface IListPresenter<V extends IItemView> {
    void onItemClick(V view);
    void bindView(V view);
    int getCount();
}
