package com.danielpclin;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.SystemUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class AppController {

    @FXML private TextArea lyricsTextArea;

    private Timer scanTimer = new Timer(true);
    private String spotifyTitle = "";

    @FXML
    public void initialize(){
        lyricsTextArea.setText("TEST");
        getTitle();
        scanTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                spotifyTitle = getTitle();
                lyricsTextArea.setText(spotifyTitle);
            }
        }, 0, 1000);
    }


    private String getTitle(){
        if(SystemUtils.IS_OS_WINDOWS){
            try{
                Process process = Runtime.getRuntime().exec("tasklist /FI \"IMAGENAME eq Spotify.exe\" /FO CSV /V");
                CSVParser csvParser = CSVParser.parse(new InputStreamReader(process.getInputStream(), Charset.defaultCharset()), CSVFormat.DEFAULT.withFirstRecordAsHeader());
                List<CSVRecord> list = csvParser.getRecords();
                for(CSVRecord csvRecord : list){
                    String title = csvRecord.get(8);
                    if(title.toLowerCase().contains("spotify")){
                        break;
                    }
                    if(title.contains("-")){
                        return title;
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            return "";
        }
        if(SystemUtils.IS_OS_MAC){
            // TODO
            return "";
        }
        if(SystemUtils.IS_OS_LINUX){
            // TODO
            return "";
        }
        return "";
    }

}
