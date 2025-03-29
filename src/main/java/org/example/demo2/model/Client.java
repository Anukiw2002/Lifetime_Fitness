package org.example.demo2.model;

import java.sql.Date;

public class Client {
    private Long id; // Matches 'id' column in client_workout table
    private Long userId; // Changed from int to Long to match database schema
    private String clientPhone;
    private String address;
    private Date dateOfBirth;
    private String emergencyContactName;
    private String emergencyContactNumber;
    private String name;

    // Constructors
    public Client() {}

    public Client(Long id, Long userId, String clientPhone, String address, Date dateOfBirth,
                  String emergencyContactName, String emergencyContactNumber, String name) {
        this.id = id;
        this.userId = userId;
        this.clientPhone = clientPhone;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactNumber = emergencyContactNumber;
        this.name = name;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getClientPhone() { return clientPhone; }
    public void setClientPhone(String clientPhone) { this.clientPhone = clientPhone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getEmergencyContactName() { return emergencyContactName; }
    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactNumber() { return emergencyContactNumber; }
    public void setEmergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}