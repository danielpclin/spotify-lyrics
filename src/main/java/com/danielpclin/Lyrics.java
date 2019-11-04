package com.danielpclin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

class Lyrics {

    static String getLyrics(String title, String artist) {
        return getLyricsMojim(title, artist);
    }

    private static String getLyricsMojim(String title, String artist) {
        title = title.replace("隠", "隱");
        List<String> lyrics = new LinkedList<>();
        try {
            Document searchDoc = Jsoup.connect("https://mojim.com/"+ title +".html?t3").get();
            Elements elements = searchDoc.select("dd.mxsh_dd2, .mxsh_dd1");
            for(Element element : elements){
                if(element.child(1).child(0).html().contains(artist)){
                    Document songDoc = Jsoup.connect("https://mojim.com/"+ element.child(3).child(0).attr("href")).get();
                    songDoc.select("br").append("br2\\n");
                    lyrics.add(removeMojimWatermark(songDoc.selectFirst("dl#fsZx1").text().replace("br2\\n", System.lineSeparator())));
                }
            }
            return lyrics.stream().max(Comparator.comparingInt(String::length)).orElse("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String removeMojimWatermark(String lyrics){
        return Arrays.stream(lyrics.split(System.lineSeparator()))
                .filter(s -> !(s.contains("Mojim.com") || s.contains("更多更詳盡歌詞")) || s.contains("提供動態歌詞"))
                .map(String::strip)
                .collect(Collectors.joining("\n"));

    }

}
