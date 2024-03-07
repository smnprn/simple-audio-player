package com.smnprn.simpleaudioplayer;

import com.smnprn.simpleaudioplayer.utils.Fonts;
import com.smnprn.simpleaudioplayer.utils.Time;
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
    private Song song;
    private boolean playing = false;

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
    private Label titleLabel;
    @FXML
    private Label artistLabel;
    @FXML
    private Label totalTimeLabel;
    @FXML
    private Label currentTimeLabel;
    @FXML
    private ProgressBar progressBar;

    public void initialize() {
        setButtonIcon(closeButton, "img/close-window.png");
        setButtonIcon(minimizeButton, "img/window-minimize.png");
        setButtonIcon(playButton, "playbutton.png");
        setButtonIcon(newSongButton, "plus.png");
        setFonts();
        setAlbumCover(null);
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
    protected void onPlayButtonClick() {
        try {
            song = new Song(
                    audioFile.getMetadata().get("title").toString(),
                    audioFile.getMetadata().get("artist").toString(),
                    (Image) audioFile.getMetadata().get("image"),
                    audioFile.getDuration()
            );

            titleLabel.setText(song.getTitle());
            artistLabel.setText(song.getArtist());
            setAlbumCover(song.getCovertArt());
            setTimeLabels();
            setProgressBar();

            if (playing) {
                stopPlaying();
            } else {
                startPlaying();
            }
        } catch (NullPointerException e) {
            System.out.println("No song selected");
        }
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

    private void setButtonIcon(Button button, String iconPath) {
        Image img = new Image(getClass().getResourceAsStream(iconPath));
        ImageView imgView = new ImageView(img);
        button.setGraphic(imgView);
        button.setBackground(null);
    }

    private void setFonts() {
        titleLabel.setFont(Fonts.TITLE);
        artistLabel.setFont(Fonts.ARTIST);
        totalTimeLabel.setFont(Fonts.TIME);
        currentTimeLabel.setFont(Fonts.TIME);
    }

    private void setTimeLabels() {
        formatTime(totalTimeLabel, Time.TOTAL);

        mediaPlayer.currentTimeProperty().addListener(ov ->
            formatTime(currentTimeLabel, Time.CURRENT)
        );
    }

    private void formatTime(Label timeLabel, Time kindOfTime) {
        song.setCurrentDuration(mediaPlayer.getCurrentTime());

        if (song.getSeconds(kindOfTime) < 10) {
            timeLabel.setText(song.durationLowSeconds(kindOfTime));
        } else {
            timeLabel.setText(song.durationHighSeconds(kindOfTime));
        }
    }

    private void setProgressBar() {
        mediaPlayer.currentTimeProperty().addListener(ov -> {
            double currentProgress = mediaPlayer.getCurrentTime().toSeconds() / mediaPlayer.getTotalDuration().toSeconds();
            progressBar.setProgress(currentProgress);
        });
    }

    private void setAlbumCover(Image coverArt) {
        Image unknownCover = new Image(getClass().getResourceAsStream("img/no-album.png"));

        if (coverArt == null) {
            albumCover.setImage(unknownCover);
        } else {
            albumCover.setImage(coverArt);
        }
    }

    private void disableLabels(boolean disable) {
        titleLabel.setDisable(disable);
        artistLabel.setDisable(disable);
    }

    private void startPlaying() {
        playing = true;
        setButtonIcon(playButton, "pausebutton.png");
        disableLabels(false);
        mediaPlayer.play();
    }

    private void stopPlaying() {
        playing = false;
        setButtonIcon(playButton, "playbutton.png");
        disableLabels(true);
        mediaPlayer.pause();
    }
}