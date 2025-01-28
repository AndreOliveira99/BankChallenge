package br.com.compass.model;

public class User {
    private String cpf;
    private String hashedPassword;
    private String name;
    private String dateOfBirth;
    private String phoneNumber;
    private Integer userId;

    // Constructor
    public User(String cpf, String hashedPassword, String name, String dateOfBirth, String phoneNumber, Integer userId) {
        this.cpf = cpf;
        this.hashedPassword = hashedPassword;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
    }

    // Getters and Setters
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getUserId() { return userId; }
    public void setUserId(String userId) { this.phoneNumber = phoneNumber; }

}
