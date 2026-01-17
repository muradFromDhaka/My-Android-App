package com.abc.studentapp.entity;

import java.util.List;

public class Student {

    // -----------------------------
    // Identification
    // -----------------------------
    private long id;
    private String name;
    private String email;
    private String password;

    // -----------------------------
    // Personal Info
    // -----------------------------
    private int age;
    private String gender;
    private String dateOfBirth;
    private String phone;
    private String address;

    // -----------------------------
    // Academic Info
    // -----------------------------
    private String course;
    private String department;
    private String skills;

    // -----------------------------
    // Preferences / Choices
    // -----------------------------
    private List<String> hobbies;
    private boolean termsAccepted;

    // -----------------------------
    // Constructors
    // -----------------------------


    public Student() { }

    // For inserting a new student (without ID)
    public Student(String name, String email, String password,
                   int age, String gender, String dateOfBirth,
                   String phone, String address,
                   String course, String department, String skills,
                   List<String> hobbies, boolean termsAccepted) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.address = address;
        this.course = course;
        this.department = department;
        this.skills = skills;
        this.hobbies = hobbies;
        this.termsAccepted = termsAccepted;
    }

    // For fetching a student from DB (with ID)
    public Student(int id, String name, String email, String password,
                   int age, String gender, String dateOfBirth,
                   String phone, String address,
                   String course, String department, String skills,
                   List<String> hobbies, boolean termsAccepted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.address = address;
        this.course = course;
        this.department = department;
        this.skills = skills;
        this.hobbies = hobbies;
        this.termsAccepted = termsAccepted;
    }

    // -----------------------------
    // Getters & Setters
    // -----------------------------

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public List<String> getHobbies() { return hobbies; }
    public void setHobbies(List<String> hobbies) { this.hobbies = hobbies; }

    public boolean isTermsAccepted() { return termsAccepted; }
    public void setTermsAccepted(boolean termsAccepted) { this.termsAccepted = termsAccepted; }
}
