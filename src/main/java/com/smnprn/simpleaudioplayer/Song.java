package com.smnprn.simpleaudioplayer;

import com.smnprn.simpleaudioplayer.utils.TimeFormatter;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Song {

    private String title;
    private String artist;
    private Duration totalDuration;
    private Duration currentDuration;
    private Image covertArt;
    private TimeFormatter totalDurationTimeFormatter;
    private TimeFormatter currentDurationTimeFormatter;
    private Time kindOfTime;

    public Song(String title, String artist, Image covertArt, Duration totalDuration) {
        this.title = title;
        this.artist = artist;
        this.covertArt = covertArt;
        this.totalDuration = totalDuration;
        totalDurationTimeFormatter = new TimeFormatter(totalDuration);
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public Duration getTotalDuration() {
        return totalDuration;
    }

    public Image getCovertArt() {
        return covertArt;
    }

    public int getSeconds() {
        return totalDurationTimeFormatter.calcSeconds();
    }

    public void setCurrentDuration(Duration currentDuration) {
        this.currentDuration = currentDuration;
        currentDurationTimeFormatter = new TimeFormatter(currentDuration);
    }

    public String durationLowSeconds(Time kindOfTime) {
        return (kindOfTime.equals(Time.TOTAL)) ? totalDurationTimeFormatter.calcMinutes() + ":" + "0" + totalDurationTimeFormatter.calcSeconds()
                                               : currentDurationTimeFormatter.calcMinutes() + ":" + "0" + currentDurationTimeFormatter.calcSeconds();
    }

    public String durationHighSeconds(Time kindOfTime) {
        return (kindOfTime.equals(Time.TOTAL)) ? totalDurationTimeFormatter.calcMinutes() + ":" + totalDurationTimeFormatter.calcSeconds()
                                               : currentDurationTimeFormatter.calcMinutes() + ":" + currentDurationTimeFormatter.calcSeconds();
    }

}
