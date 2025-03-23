package com.example.holaorder.Model;

public class Cart {
    private String name;
    private String price;
    private String quantity;
    private String Image;

    public Cart() {
    }

    public Cart(String name, String price, String quantity, String image) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.Image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }
}


