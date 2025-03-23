package com.example.sportshop.Model;

public class User {
    private String Name;
    private String Password;
    private String Phone;
    private String Image;
    private String Email;

    public User() {
    }

    public User(String name, String password, String phone, String image, String email) {
        Name = name;
        Password = password;
        Phone = phone;
        Image = image;
        Email = email;
    }

    public User(String name, String phone, String email) {
        Name = name;
        Phone = phone;
        Email = email;
    }

    public User(String name, String password) {
        Name = name;
        Password = password;
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "Name='" + Name + '\'' +
                ", Password='" + Password + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Image='" + Image + '\'' +
                ", Email='" + Email + '\'' +
                '}';
    }
}
