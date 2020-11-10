package ru.geekbrains.githubclient.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import ru.geekbrains.githubclient.R;
import ru.geekbrains.githubclient.mvp.presenter.ShowUserPresenter;
import ru.geekbrains.githubclient.mvp.view.ShowUserView;
import ru.geekbrains.githubclient.ui.BackButtonListener;

public class ShowUserFragment extends MvpAppCompatFragment implements ShowUserView, BackButtonListener {
    private static String BUNDLE_KEY_LOGIN = "ShowUserFragment.login";

    private View view;
    private TextView userLogin;

    @InjectPresenter
    ShowUserPresenter showUserPresenter;

    public static ShowUserFragment getInstance(String login) {
        ShowUserFragment fragment = new ShowUserFragment();

        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_LOGIN, login);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null) {
            String login = bundle.getString(BUNDLE_KEY_LOGIN);
            showUserPresenter.configure(login);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_user, container, false);
        userLogin = view.findViewById(R.id.user_login);

        return view;
    }

    @Override
    public void setLogin(String login) {
        userLogin.setText(login);
    }

    @Override
    public boolean backPressed() {
        return showUserPresenter.backPressed();
    }
}
