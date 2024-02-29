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
import javafx.util.Duration;

import java.io.File;

public class Controller {

    private final FileChooser fileChooser = new FileChooser();
    private Media audioFile = null;
    private MediaPlayer mediaPlayer = null;

    @FXML
    private Button playButton;
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
    private ProgressBar progressBar;

    private boolean playing = false;

    public void initialize() {
        setButtonIcon(closeButton, "img/close-window.png");
        setButtonIcon(minimizeButton, "img/window-minimize.png");
        setButtonIcon(playButton, "playbutton.png");
        setButtonIcon(newSongButton, "plus.png");
        setFonts();
    }

    @FXML
    protected void onPlayButtonClick() {
        title.setText(audioFile.getMetadata().get("title").toString());
        artist.setText(audioFile.getMetadata().get("artist").toString());
        setAlbumCover();
        setSongTimes();
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
    public void onMinimizeButtonClick() {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void onCloseButtonClick() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void setButtonIcon(Button button, String iconPath) {
        Image img = new Image(getClass().getResourceAsStream(iconPath));
        ImageView imgView = new ImageView(img);
        button.setGraphic(imgView);
        button.setBackground(null);
    }

    public void setFonts() {
        title.setFont(Fonts.TITLE);
        artist.setFont(Fonts.ARTIST);
        time.setFont(Fonts.TIME);
        currentTime.setFont(Fonts.TIME);
    }

    public void setSongTimes() {
        formatTime(audioFile.getDuration(), time);

        mediaPlayer.currentTimeProperty().addListener(ov -> {
            formatTime(mediaPlayer.getCurrentTime(), currentTime);
        });
    }

    public void formatTime(Duration songDuration, Label time) {
        TimeFormatter timeFormatter = new TimeFormatter(songDuration);

        if (timeFormatter.calcSeconds() < 10) {
            time.setText(timeFormatter.calcMinutes() + ":" + "0" + timeFormatter.calcSeconds());
        } else {
            time.setText(timeFormatter.calcMinutes() + ":" + timeFormatter.calcSeconds());
        }
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

    public void disableLabels(boolean disable) {
        title.setDisable(disable);
        artist.setDisable(disable);
    }

    public void startPlaying() {
        playing = true;
        setButtonIcon(playButton, "pausebutton.png");
        disableLabels(false);
        mediaPlayer.play();
    }

    public void stopPlaying() {
        playing = false;
        setButtonIcon(playButton, "playbutton.png");
        disableLabels(true);
        mediaPlayer.pause();
    }
}