package ru.geekbrains.githubclient.mvp.presenter;

import android.util.Log;

import ru.geekbrains.githubclient.R;
import ru.geekbrains.githubclient.mvp.model.Model;
import ru.geekbrains.githubclient.mvp.view.MainView;

public class Presenter {
    public final int BUTTONS_QTTY = 3;
    private final String LOG_TAG = "githubclient_Presenter";

    private final MainView view;
    private final Model model = new Model(BUTTONS_QTTY);

    public Presenter(MainView view) {
        this.view = view;
    }

    public void counterClick(int id) {
        if (!isIdCorrect(id)) {
            Log.e(LOG_TAG, "Button index out of bounds, id = " + id);
            return;
        }

        int value = model.getCurrent(id);

        value = processValue(value);

        model.set(id, value);
        view.setButtonText(id, String.valueOf(value));
    }

    private int processValue(int value) {
        return value + 1;
    }

    private boolean isIdCorrect(int id) {
        return 0 <= id && id < BUTTONS_QTTY;
    }
}
