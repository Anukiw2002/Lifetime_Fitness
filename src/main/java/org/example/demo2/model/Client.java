package org.example.demo2.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Base64;

public class Client {
    private Long id;
    private Long userId;
    private String phoneNumber;
    private String address;
    private String dateOfBirth;
    private String emergencyContactName;
    private String emergencyContactNumber;
    private String gender;
    private String name; // We'll keep this for convenience, retrieving from users table
    private String email;
    private String username;// We'll keep this for convenience, retrieving from users table
    private String houseNo;
    private String streetName;
    private String city;
    private String firstName;
    private byte[] profilePicture;
    private String profilePictureBase64;

    // Constructors
    public Client() {}

    public Client(Long userId, String phoneNumber, String address, String dateOfBirth,
                  String emergencyContactName, String emergencyContactNumber) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactNumber = emergencyContactNumber;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getEmergencyContactName() { return emergencyContactName; }
    public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }
    public String getEmergencyContactNumber() { return emergencyContactNumber; }
    public void setEmergencyContactNumber(String emergencyContactNumber) { this.emergencyContactNumber = emergencyContactNumber; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getHouseNo() { return houseNo; }
    public void setHouseNo(String houseNo) { this.houseNo = houseNo; }
    public String getStreetName() { return streetName; }
    public void setStreetName(String streetName) { this.streetName = streetName; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getFirstName() { return firstName; }

    public byte[] getProfilePicture() { return profilePicture; }
    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
        if (profilePicture != null) {
            this.profilePictureBase64 = Base64.getEncoder().encodeToString(profilePicture);
        }
    }

    public String getProfilePictureBase64() { return profilePictureBase64; }
    public void setProfilePictureBase64(String profilePictureBase64) { this.profilePictureBase64 = profilePictureBase64; }

    public int getAge(){
        if (dateOfBirth == null) return 0;
        return Period.between(LocalDate.parse(dateOfBirth), LocalDate.now()).getYears();
    }
}