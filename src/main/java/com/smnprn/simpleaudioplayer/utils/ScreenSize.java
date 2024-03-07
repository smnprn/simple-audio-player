package com.smnprn.simpleaudioplayer.utils;

import javafx.stage.Screen;

public enum ScreenSize {

    SCREEN_WIDTH(Screen.getPrimary().getBounds().getWidth()),
    SCREEN_HEIGHT(Screen.getPrimary().getBounds().getHeight());

    public final double length;

    ScreenSize(double length) {
        this.length = length;
    }

}
