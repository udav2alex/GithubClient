package ru.geekbrains.githubclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.githubclient.mvp.presenter.Presenter;
import ru.geekbrains.githubclient.mvp.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    private Presenter presenter;

    private final List<Button> buttons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new Presenter(this);

        initButtons();
    }

    @Override
    public void setButtonText(int index, String text) {
        buttons.get(index).setText(text);
    }

    private void initButtons() {
        for (int i = 0; i < presenter.BUTTONS_QTTY; i++) {
            // Манипуляции с идентификаторами. Не очень красиво...
            String buttonId = "btn_counter" + (i+1);
            int resId = getResources().getIdentifier(buttonId, "id", getPackageName());

            Button button = findViewById(resId);
            button.setOnClickListener(new ButtonClickListener(i));
            buttons.add(button);
        }
    }

    private class ButtonClickListener implements View.OnClickListener {

        private final int index;

        public ButtonClickListener(int buttonIndex) {
            this.index = buttonIndex;
        }

        @Override
        public void onClick(View v) {
            presenter.counterClick(index);
        }
    }
}