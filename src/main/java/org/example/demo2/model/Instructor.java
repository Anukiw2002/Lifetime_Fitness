package org.example.demo2.model;

import java.util.Base64;
import java.util.Date;
import java.util.List;

public class Instructor {
    private int userId;
    private String firstName;
    private String surname;
    private String email;
    private boolean isActive;
    private Date dateOfBirth;
    private String phoneNumber;
    private String emergencyContactName;
    private String emergencyContactRelationship;
    private String emergencyContactNumber;
    private String onboardingStatus;
    private String houseNumber;
    private String streetName;
    private String city;
    private byte[] profilePicture;
    private String profilePictureBase64;
    private List<Certificate> certificates;


    public String getFirstName() { return firstName; }
    public String getSurname() { return surname; }
    public String getEmail() { return email; }
    public boolean getIsActive() { return isActive; }
    public Date getDateOfBirth() { return dateOfBirth; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmergencyContactName() { return emergencyContactName; }
    public String getEmergencyContactRelationship() { return emergencyContactRelationship; }
    public String getEmergencyContactNumber() { return emergencyContactNumber; }
    public String getOnboardingStatus() { return onboardingStatus; }
    public String getHouseNumber() { return houseNumber; }
    public String getStreetName() { return streetName; }
    public String getCity() { return city; }
    public byte[] getProfilePicture() { return profilePicture; }
    public String getProfilePictureBase64() { return profilePictureBase64; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setEmail(String email) { this.email = email; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }
    public void setEmergencyContactRelationship(String emergencyContactRelationship) { this.emergencyContactRelationship = emergencyContactRelationship; }
    public void setEmergencyContactNumber(String emergencyContactNumber) { this.emergencyContactNumber = emergencyContactNumber; }
    public void setOnboardingStatus(String onboardingStatus) { this.onboardingStatus = onboardingStatus; }
    public void setHouseNumber(String houseNumber) { this.houseNumber = houseNumber; }
    public void setStreetName(String streetName) { this.streetName = streetName; }
    public void setCity(String city) { this.city = city; }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
        if (profilePicture != null) {
            this.profilePictureBase64 = Base64.getEncoder().encodeToString(profilePicture);
        }
    }

    public void setProfilePictureBase64(String profilePictureBase64) { this.profilePictureBase64 = profilePictureBase64; }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }

    public List<Certificate> getCertificates() {
        return certificates;
    }

}