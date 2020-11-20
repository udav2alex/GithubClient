package ru.geekbrains.githubclient.mvp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Model {
    private final List<Integer> counters;

    public Model(int buttonsQtty) {
        counters = new ArrayList<>();
        for (int i = 0; i < buttonsQtty; i++) {
            counters.add(0);
        }
    }

    public int getCurrent(int index) {
        return counters.get(index);
    }

    public void set(int index, int value) {
        counters.set(index, value);
    }
}
