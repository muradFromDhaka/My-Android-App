package com.abc.campustrack.entity;

import java.util.List;

public class CampusTrack {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String dateOfBirth;
    private String gender;

    private String course;
    private float gpa;
    private List<String> hobbies;
    private String skills;
    private boolean isTermsAccepted;

    public CampusTrack() {
    }

    public CampusTrack(String name, String email, String password, String phone,
                       String address, String dateOfBirth,String gender, String course, float gpa,
                       List<String> hobbies, String skills, boolean isTermsAccepted) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.course = course;
        this.gpa = gpa;
        this.hobbies = hobbies;
        this.skills = skills;
        this.isTermsAccepted = isTermsAccepted;
    }


    public CampusTrack(Long id, String name, String email, String password, String phone,
                       String address, String dateOfBirth,String gender, String course, float gpa,
                       List<String> hobbies, String skills, boolean isTermsAccepted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.course = course;
        this.gpa = gpa;
        this.hobbies = hobbies;
        this.skills = skills;
        this.isTermsAccepted = isTermsAccepted;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCourse() {
        return course;
    }

    public float getGpa() {
        return gpa;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public String getSkills() {
        return skills;
    }

    public boolean isTermsAccepted() {
        return isTermsAccepted;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public void setTermsAccepted(boolean termsAccepted) {
        isTermsAccepted = termsAccepted;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
