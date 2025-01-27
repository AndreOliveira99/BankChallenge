package br.com.compass.model;

public class User {
    private String cpf;
    private String hashedPassword;
    private String name;
    private String dateOfBirth;
    private String phoneNumber;

    // Constructor
    public User(String cpf, String hashedPassword, String name, String dateOfBirth, String phoneNumber) {
        this.cpf = cpf;
        this.hashedPassword = hashedPassword;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public String getCpf() { return cpf; }
    public String getHashedPassword() { return hashedPassword; }
    public String getName() { return name; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getPhoneNumber() { return phoneNumber; }
}
