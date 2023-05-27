package com.example.bbs.ui.setting;

public class Music {

    private String music_name;
    private String music_adult;
    private Integer music_path;

    public Music(String music_name, String music_adult, Integer music_path) {
        this.music_name = music_name;
        this.music_adult = music_adult;
        this.music_path = music_path;
    }

    public String getMusic_name() {
        return music_name;
    }

    public void setMusic_name(String music_name) {
        this.music_name = music_name;
    }

    public String getMusic_adult() {
        return music_adult;
    }

    public void setMusic_adult(String music_adult) {
        this.music_adult = music_adult;
    }

    public Integer getMusic_path() {
        return music_path;
    }

    public void setMusic_path(Integer music_path) {
        this.music_path = music_path;
    }
}
