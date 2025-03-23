package com.example.holaorder.Model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Food {
    private String Id;
    private String Name;
    private String Image;
    private String CategoryId;
    private String Description;
    private String Discount;
    private String Price;
    private String Rate;

    public Food() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Food(String name, String image, String categoryId, String description, String discount, String price, String rate) {
        Name = name;
        Image = image;
        CategoryId = categoryId;
        Description = description;
        Discount = discount;
        Price = price;
        Rate = rate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    @Override
    public String toString() {
        return "Food{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", Image='" + Image + '\'' +
                ", CategoryId='" + CategoryId + '\'' +
                ", Description='" + Description + '\'' +
                ", Discount='" + Discount + '\'' +
                ", Price='" + Price + '\'' +
                ", Rate='" + Rate + '\'' +
                '}';
    }
}
