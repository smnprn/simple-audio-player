package com.smnprn.simpleaudioplayer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller {

    private FileChooser fileChooser = new FileChooser();
    //private File chosenFile;
    private Media audioFile = null;
    private MediaPlayer mediaPlayer = null;

    @FXML
    private Button newSongButton;
    @FXML
    private ImageView albumCover;
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
    @FXML
    private ProgressBar progressBar;

    private boolean playing = false;

    public void initialize() {
        setPlayButton();
        setNewSongButton();
        setFonts();
    }

    @FXML
    protected void onPlayButtonClick() {
        setAlbumCover();
        setCurrentTime();
        setProgressBar();
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

    @FXML
    public void onNewSongButton() {
        File chosenFile = fileChooser.showOpenDialog(newSongButton.getScene().getWindow());
        audioFile = new Media(chosenFile.toURI().toString());
        mediaPlayer = new MediaPlayer(audioFile);
        System.out.println(chosenFile.getAbsoluteFile());
        System.out.println(audioFile.getMetadata().toString());
    }

    public void setPlayButton() {
        Image img = new Image(getClass().getResourceAsStream("playbutton.png"));
        ImageView imgView = new ImageView(img);
        playButton.setGraphic(imgView);
        playButton.setBackground(null);
    }

    public void setNewSongButton() {
        Image img = new Image(getClass().getResourceAsStream("plus.png"));
        ImageView imgView = new ImageView(img);
        newSongButton.setGraphic(imgView);
        newSongButton.setBackground(null);
    }

    public void setPauseButton() {
        Image img = new Image(getClass().getResourceAsStream("pausebutton.png"));
        ImageView imgView = new ImageView(img);
        playButton.setGraphic(imgView);
    }

    public void setFonts() {
        title.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 26));
        artist.setFont(Font.font("Helvetica Neue", FontWeight.NORMAL,18));
        time.setFont(Font.font("Helvetica Neue", FontWeight.NORMAL,12)); // Font da rivedere
        currentTime.setFont(Font.font("Helvetica Neue", FontWeight.NORMAL,12));
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
                currentTime.setText(timeFormatter.calcMinutes() + ":" + "0" + timeFormatter.calcSeconds());
            } else {
                currentTime.setText(timeFormatter.calcMinutes() + ":" + timeFormatter.calcSeconds());
            }
        });
    }

    public void setProgressBar() {
        mediaPlayer.currentTimeProperty().addListener(ov -> {
            double currentProgress = mediaPlayer.getCurrentTime().toSeconds() / mediaPlayer.getTotalDuration().toSeconds();
            progressBar.setProgress(currentProgress);
        });
    }

    public void setTitle(String text) {
        title.setText(text);
    }

    public void setArtist(String text) {
        artist.setText(text);
    }

    public void setAlbumCover() {
        if (audioFile.getMetadata().get("image") == null) {
            albumCover.setImage(new Image(getClass().getResourceAsStream("no-album.png")));
        } else {
            albumCover.setImage((Image) audioFile.getMetadata().get("image"));
        }
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