package com.example.shakeawaythestress.models;

public class GridOption {
    private String text;
    private String imageName;

    public GridOption(String text, String imageName) {
        this.text = text;
        this.imageName = imageName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
