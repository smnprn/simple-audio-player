package com.smnprn.simpleaudioplayer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;

import java.io.File;

public class Controller {

    private String audioPath = "/home/smnprn/IdeaProjects/simple-audio-player/src/main/java/com/smnprn/simpleaudioplayer/assets/oip.mp3";
    private final Media audioFile = new Media(new File(audioPath).toURI().toString());
    private final MediaPlayer mediaPlayer = new MediaPlayer(audioFile);

    @FXML
    private Label title;
    @FXML
    private Label artist;
    @FXML
    private Label time;
    @FXML
    private Label currentTime;
    @FXML
    private Button playButton;

    private boolean playing = false;

    public void initialize() {
        setPlayButton();
        playButton.setBackground(null);
        setFonts();
    }

    @FXML
    protected void onPlayButtonClick() {
        setCurrentTime();
        if (playing) {
            playing = false;
            setPlayButton();
            disableLabels();
            mediaPlayer.pause();
        } else {
            playing = true;
            setPauseButton();
            enableLabels();
            setTitle(audioFile.getMetadata().get("title").toString());
            setArtist(audioFile.getMetadata().get("artist").toString());
            setTime();
            mediaPlayer.play();
        }
    }

    public void setPlayButton() {
        Image img = new Image(getClass().getResourceAsStream("playbutton.png"));
        ImageView imgView = new ImageView(img);
        playButton.setGraphic(imgView);
    }

    public void setPauseButton() {
        Image img = new Image(getClass().getResourceAsStream("pausebutton.png"));
        ImageView imgView = new ImageView(img);
        playButton.setGraphic(imgView);
    }

    public void setFonts() {
        title.setFont(new Font("VCR OSD Mono",36));
        artist.setFont(new Font("VCR OSD Mono", 22));
        time.setFont(new Font("VCR OSD Mono", 12)); // Font da rivedere
        currentTime.setFont(new Font("VCR OSD Mono", 12));
    }

    public void setTime() {
        // Da rivedere quando verrà implementata la scelta della canzone
        TimeFormatter timeFormatter = new TimeFormatter(audioFile.getDuration());
        time.setText(timeFormatter.calcMinutes() + ":" + timeFormatter.calcSeconds());
    }

    public void setCurrentTime() {
        mediaPlayer.currentTimeProperty().addListener(ov -> {
            TimeFormatter timeFormatter = new TimeFormatter(mediaPlayer.getCurrentTime());

            if (timeFormatter.calcSeconds() < 10) {
                currentTime.setText(timeFormatter.calcMinutes() + ":" + "0" + timeFormatter.calcSeconds() + " / ");
            } else {
                currentTime.setText(timeFormatter.calcMinutes() + ":" + timeFormatter.calcSeconds() + " / ");
            }
        });
    }

    public void setTitle(String text) {
        title.setText(text);
    }

    public void setArtist(String text) {
        artist.setText(text);
    }

    public void resetTexts() {
        title.setText("");
        artist.setText("");
    }

    public void enableLabels() {
        title.setDisable(false);
        artist.setDisable(false);
    }

    public void disableLabels() {
        title.setDisable(true);
        artist.setDisable(true);
    }
}