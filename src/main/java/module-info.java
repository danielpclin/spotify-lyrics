module com.danielpclin {
    requires javafx.fxml;
    requires javafx.controls;
    requires commons.csv;
    requires commons.lang3;
    requires org.jsoup;

    opens com.danielpclin.spotifyLyrics;
    exports com.danielpclin.spotifyLyrics;
}