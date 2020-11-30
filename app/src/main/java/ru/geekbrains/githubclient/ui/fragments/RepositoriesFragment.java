package ru.geekbrains.githubclient.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.geekbrains.githubclient.GithubApplication;
import ru.geekbrains.githubclient.R;
import ru.geekbrains.githubclient.mvp.model.entity.GithubUser;
import ru.geekbrains.githubclient.mvp.model.entity.room.GithubDatabase;
import ru.geekbrains.githubclient.mvp.model.repo.retrofit.RetrofitGithubRepositoriesRepo;
import ru.geekbrains.githubclient.mvp.presenter.RepositoriesPresenter;
import ru.geekbrains.githubclient.mvp.view.RepositoriesView;
import ru.geekbrains.githubclient.ui.BackButtonListener;
import ru.geekbrains.githubclient.ui.adapter.RepositoryRVAdapter;
import ru.geekbrains.githubclient.ui.network.AndroidNetworkStatus;

public class RepositoriesFragment extends MvpAppCompatFragment implements RepositoriesView, BackButtonListener {
    private static final String BUNDLE_KEY_GITHUB_USER = "ShowUserFragment.GithubUser";

    private TextView userLogin;
    private TextView reposCount;
    private RecyclerView recyclerView;

    @InjectPresenter
    RepositoriesPresenter repositoriesPresenter;

    @ProvidePresenter
    RepositoriesPresenter getRepositoriesPresenter() {
        return new RepositoriesPresenter(
              AndroidSchedulers.mainThread(),
              new RetrofitGithubRepositoriesRepo(
                    GithubApplication.INSTANCE.getApi().getDataSource(),
                    new AndroidNetworkStatus(),
                    GithubDatabase.getInstance()),
              GithubApplication.INSTANCE.getRouter()
        );
    }

    public static RepositoriesFragment getInstance(GithubUser githubUser) {
        RepositoriesFragment fragment = new RepositoriesFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_KEY_GITHUB_USER, githubUser);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null) {
            GithubUser githubUser = bundle.getParcelable(BUNDLE_KEY_GITHUB_USER);
            repositoriesPresenter.configure(githubUser);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repositories, container, false);

        userLogin = view.findViewById(R.id.tv_user_login);
        reposCount = view.findViewById(R.id.tv_repos_count);
        recyclerView = view.findViewById(R.id.rv_repositories);

        return view;
    }

    @Override
    public void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(
            new RepositoryRVAdapter(repositoriesPresenter.getRepositoriesListPresenter()));
    }

    @Override
    public void setLogin(String login) {
        userLogin.setText(login);
    }

    @Override
    public void setReposCount(int count) {
        String string = count + " "
            + (count == 1 ?
            getString(R.string.caption_repository)
            : getString(R.string.caption_repositories))
            + ":";
        reposCount.setText(string);
    }

    @Override
    public void updateList() {
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void showError(String description) {

    }

    @Override
    public void onStop() {
        super.onStop();
        repositoriesPresenter.onStop();
    }

    @Override
    public boolean backPressed() {
        return repositoriesPresenter.backPressed();
    }
}
