package com.smnprn.simpleaudioplayer;

import javafx.util.Duration;

public class TimeFormatter {

    private Duration songDuration;

    public TimeFormatter(Duration songDuration) {
        this.songDuration = songDuration;
    }

    private double convertToSeconds(Duration songDuration) {
        return songDuration.toSeconds();
    }

    private int roundToInt(double seconds) {
        return (int) seconds;
    }

    public int calcMinutes() {
        int durationInSeconds = roundToInt(convertToSeconds(songDuration));
        return durationInSeconds / 60;
    }

    public int calcSeconds() {
        int durationInSeconds = roundToInt(convertToSeconds(songDuration));
        return durationInSeconds - (calcMinutes() * 60);
    }
}
