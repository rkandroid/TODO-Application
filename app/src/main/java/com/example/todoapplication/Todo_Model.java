package com.example.todoapplication;

public class Todo_Model {
    int id;
    String name, number, city;

    public Todo_Model(int id, String name, String number, String city) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.city = city;
    }

    public Todo_Model(String name, String number, String city) {
        this.name = name;
        this.number = number;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(String name) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}