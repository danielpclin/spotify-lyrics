package com.danielpclin;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.SystemUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

class Window {

    static String getTitle() {
        if (SystemUtils.IS_OS_WINDOWS) {
            return getWindowsTitle();
        }
//        if (SystemUtils.IS_OS_MAC) {
//            return getMacTitle();
//        }
//        if (SystemUtils.IS_OS_LINUX) {
//            return getLinuxTitle();
//        }
        throw new UnsupportedOperationException("OS not supported");
    }

    private static String getWindowsTitle() {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getMacTitle() {
        // TODO
        return "";
    }

    private static String getLinuxTitle() {
        // TODO
        return "";
    }

}
