package ru.geekbrains.githubclient.mvp.presenter;

import ru.geekbrains.githubclient.R;
import ru.geekbrains.githubclient.mvp.model.Model;
import ru.geekbrains.githubclient.mvp.view.MainView;

public class Presenter {
    private MainView view;
    private Model model = new Model();

    public Presenter(MainView view) {
        this.view = view;
    }

    public void counterClick(int id) {
        switch (id) {
            case R.id.btn_counter1:
                view.setButtonText(0, String.valueOf(model.next(0)));
                break;
            case R.id.btn_counter2:
                view.setButtonText(1, String.valueOf(model.next(1)));
                break;
            case R.id.btn_counter3:
                view.setButtonText(2, String.valueOf(model.next(2)));
                break;

        }
    }
}
