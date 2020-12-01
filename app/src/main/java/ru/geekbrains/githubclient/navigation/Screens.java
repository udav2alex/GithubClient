package ru.geekbrains.githubclient.navigation;

import androidx.fragment.app.Fragment;

import ru.geekbrains.githubclient.mvp.model.entity.GithubUser;
import ru.geekbrains.githubclient.mvp.model.entity.UserRepository;
import ru.geekbrains.githubclient.ui.fragments.RepositoriesFragment;
import ru.geekbrains.githubclient.ui.fragments.ShowRepositoryFragment;
import ru.geekbrains.githubclient.ui.fragments.UsersFragment;
import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {
    public static class UsersScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return new UsersFragment();
        }
    }

    public static class RepositoriesScreen extends SupportAppScreen {
        private final GithubUser githubUser;

        public RepositoriesScreen(GithubUser githubUser) {
            super();
            this.githubUser = githubUser;
        }

        @Override
        public Fragment getFragment() {
            return RepositoriesFragment.getInstance(githubUser);
        }
    }

    public static class ShowRepositoryScreen extends SupportAppScreen {
        private final UserRepository userRepository;

        public ShowRepositoryScreen(UserRepository userRepository) {
            super();
            this.userRepository = userRepository;
        }

        @Override
        public Fragment getFragment() {
            return ShowRepositoryFragment.getInstance(userRepository);
        }
    }
}
