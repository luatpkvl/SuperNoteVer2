package com.example.supernote.model;

public class Book {
    private int id;
    private String tensach;
    private int price;

    public Book(int id, String tensach, int price) {
        this.id = id;
        this.tensach = tensach;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
