package com.danielpclin.spotifyLyrics;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.SystemUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

class Window {

    static String getTitle() {
        if (SystemUtils.IS_OS_WINDOWS) {
            return getWindowsTitle();
        }
        if (SystemUtils.IS_OS_MAC) {
            return getMacTitle();
        }
        if (SystemUtils.IS_OS_LINUX) {
            return getLinuxTitle();
        }
        throw new UnsupportedOperationException("OS not supported");
    }

    private static String getWindowsTitle() {
        try{
            Process process = Runtime.getRuntime().exec("tasklist /FI \"IMAGENAME eq Spotify.exe\" /FO CSV /V");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.defaultCharset()));
            String line = null;
            StringBuilder builder = new StringBuilder();
            while ( (line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            String result = builder.toString();
            CSVParser csvParser = CSVParser.parse(result, CSVFormat.DEFAULT.withFirstRecordAsHeader());
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
        } catch (IOException e) {
            System.out.println("Handling Error:");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return "";
    }

    private static String getMacTitle() {
        throw new UnsupportedOperationException("OS not supported");
    }

    private static String getLinuxTitle() {
        throw new UnsupportedOperationException("OS not supported");
    }

}
