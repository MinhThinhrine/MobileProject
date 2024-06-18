package com.example.appbanthietbidientu.model;

public class Item_admin {
    int icon;
    String text;

    public Item_admin(int icon, String text) {
        this.icon = icon;
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Item_admin{" +
                "icon=" + icon +
                ", text='" + text + '\'' +
                '}';
    }
}
