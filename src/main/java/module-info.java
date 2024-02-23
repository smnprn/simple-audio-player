module com.smnprn.simpleaudioplayer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires javafx.media;

    opens com.smnprn.simpleaudioplayer to javafx.fxml;
    exports com.smnprn.simpleaudioplayer;
}