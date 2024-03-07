module com.smnprn.simpleaudioplayer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires javafx.media;
    requires reload4j;

    opens com.smnprn.simpleaudioplayer to javafx.fxml;
    exports com.smnprn.simpleaudioplayer;
    exports com.smnprn.simpleaudioplayer.utils;
    opens com.smnprn.simpleaudioplayer.utils to javafx.fxml;
}