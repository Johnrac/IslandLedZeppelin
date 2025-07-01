package com.javarush.island.pukhov.services;


import com.javarush.island.pukhov.exception.service.ViewServiceException;
import com.javarush.island.pukhov.view.View;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ViewService implements Runnable {

    private final View view;

    @Override
    public void run() {
        try {
            view.showStatistic();
            view.showIsland();
        } catch (RuntimeException e) {
            throw new ViewServiceException(e);
        }
    }
}
