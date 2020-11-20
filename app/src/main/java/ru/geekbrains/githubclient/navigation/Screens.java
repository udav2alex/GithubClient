package ru.geekbrains.githubclient.navigation;

import androidx.fragment.app.Fragment;

import ru.geekbrains.githubclient.mvp.model.entity.GithubUser;
import ru.geekbrains.githubclient.ui.fragments.ShowUserFragment;
import ru.geekbrains.githubclient.ui.fragments.UsersFragment;
import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {
    public static class UsersScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return UsersFragment.getInstance(0);
        }
    }

    public static class ViewUserScreen extends SupportAppScreen {
        private final GithubUser githubUser;

        public ViewUserScreen(GithubUser githubUser) {
            super();
            this.githubUser = githubUser;
        }

        @Override
        public Fragment getFragment() {
            return ShowUserFragment.getInstance(githubUser);
        }
    }
}
