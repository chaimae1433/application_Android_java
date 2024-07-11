package com.kaadiri.examtp2022;

public class Person {
    Integer ID;
    String firstName;
    String lastName;
    String birthDay;
    byte [] image;
    String email;

    public Person(Integer id, String firstName, String lastName, String birthDay, byte[] image, String email) {
        this.ID = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.image = image;
        this.email = email;
    }
    public Person(String firstName, String lastName, String birthDay, byte[] image, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.image = image;
        this.email = email;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer id) {
        this.ID = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
