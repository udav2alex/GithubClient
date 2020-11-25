package ru.geekbrains.githubclient.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;

@AddToEndSingle
public interface ShowRepository extends MvpView {
    void shopForkCount(int count);
}
