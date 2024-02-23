package com.smnprn.simpleaudioplayer;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

public class ElementsHandler {

    @FXML
    private Label title;
    @FXML
    private Label artist;
    @FXML
    private Button playButton;

    public void setButton() {
        Image img = new Image(getClass().getResourceAsStream("playbutton.png"));
        ImageView imgView = new ImageView(img);
        playButton.setGraphic(imgView);
        playButton.setBackground(null);
    }

    public void setFonts() {
        title.setFont(new Font("VCR OSD Mono",36));
        artist.setFont(new Font("VCR OSD Mono", 22));
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
