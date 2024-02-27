package com.smnprn.simpleaudioplayer;

import com.smnprn.simpleaudioplayer.utils.Fonts;
import com.smnprn.simpleaudioplayer.utils.TimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {

    private final FileChooser fileChooser = new FileChooser();
    private Media audioFile = null;
    private MediaPlayer mediaPlayer = null;

    @FXML
    private Button closeButton;
    @FXML
    private Button minimizeButton;
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
        setWindowButtons();
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
            stopPlaying();
        } else {
            startPlaying();
        }
    }

    @FXML
    public void onNewSongButton() {
        if (playing) {
            stopPlaying();
        }

        File chosenAudioFile = fileChooser.showOpenDialog(newSongButton.getScene().getWindow());
        audioFile = new Media(chosenAudioFile.toURI().toString());
        mediaPlayer = new MediaPlayer(audioFile);
    }

    @FXML
    public void onMinimizeButtonCLick() {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void onCloseButtonClick() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void setWindowButtons() {
        Image img = new Image(getClass().getResourceAsStream("img/close-window.png"));
        ImageView imgView = new ImageView(img);
        closeButton.setGraphic(imgView);
        closeButton.setBackground(null);

        img = new Image(getClass().getResourceAsStream("img/window-minimize.png"));
        imgView = new ImageView(img);
        minimizeButton.setGraphic(imgView);
        minimizeButton.setBackground(null);
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
        title.setFont(Fonts.TITLE);
        artist.setFont(Fonts.ARTIST);
        time.setFont(Fonts.TIME);
        currentTime.setFont(Fonts.TIME);
    }

    public void setTime() {
        TimeFormatter timeFormatter = new TimeFormatter(audioFile.getDuration());

        if (timeFormatter.calcSeconds() < 10) {
            time.setText(timeFormatter.calcMinutes() + ":" + "0" + timeFormatter.calcSeconds());
        } else {
            time.setText(timeFormatter.calcMinutes() + ":" + timeFormatter.calcSeconds());
        }
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

    public void startPlaying() {
        playing = true;
        setPauseButton();
        enableLabels();
        title.setText(audioFile.getMetadata().get("title").toString());
        artist.setText(audioFile.getMetadata().get("artist").toString());
        setTime();
        mediaPlayer.play();
    }

    public void stopPlaying() {
        playing = false;
        setPlayButton();
        disableLabels();
        mediaPlayer.pause();
    }
}