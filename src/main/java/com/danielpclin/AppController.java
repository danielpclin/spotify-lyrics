package com.danielpclin;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.Timer;
import java.util.TimerTask;

public class AppController {

    @FXML private TextArea lyricsTextArea;

    private Timer scanTimer = new Timer(true);
    private String spotifyTitle = "";

    @FXML
    public void initialize(){
        scanTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkSongAndUpdateTextArea();
            }
        }, 0, 1000);
    }

    private void checkSongAndUpdateTextArea() {
        String currentSpotifyTitle = Window.getTitle();
        if(!currentSpotifyTitle.equals(spotifyTitle) && !currentSpotifyTitle.equals("")){
            String title, artist;
            String[] spotifyTitleArr = currentSpotifyTitle.split(" - ");
            title = spotifyTitleArr[1];
            artist = spotifyTitleArr[0];
            System.out.println(title + " - " + artist);
            lyricsTextArea.setText(Lyrics.getLyrics(title, artist));
            spotifyTitle = currentSpotifyTitle;
        }
    }

}
