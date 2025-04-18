package org.example.demo2.model;

import java.util.Base64;
import java.util.Date;

public class Instructor {
    private String firstName;
    private String surname;
    private String email;
    private boolean isActive;
    private Date dateOfBirth;
    private String phoneNumber;
    String emergencyContactName;
    String EmergencyContactRelationship;
    String EmergencyContactNumber;
    String emergencyContactNumber;
    String onboardingStatus;
    String houseNumber;
    String streetName;
    String city;
    private byte[] profilePicture;
    private String profilePictureBase64;

    public String getFirstName() { return firstName; }
    public String getSurname() { return surname; }
    public String getEmail() { return email; }

    public void setFirstName(String firstName) {this.firstName = firstName;}
    public void setSurname(String surname) {this.surname = surname;}
    public void setEmail(String email) {this.email = email;}
    public void setIsActive(boolean isActive) {this.isActive = isActive;}

    public byte[] getProfilePicture() { return profilePicture; }
    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
        if (profilePicture != null) {
            this.profilePictureBase64 = Base64.getEncoder().encodeToString(profilePicture);
        }
    }

    public boolean getIsActive() {
        return isActive;
    }

    public String getProfilePictureBase64() { return profilePictureBase64; }
    public void setProfilePictureBase64(String profilePictureBase64) { this.profilePictureBase64 = profilePictureBase64; }


}

