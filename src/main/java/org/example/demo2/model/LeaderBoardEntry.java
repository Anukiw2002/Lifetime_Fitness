package org.example.demo2.model;

import java.util.Base64;

public class LeaderBoardEntry {
    private String full_name;
    private int amount;
    private byte[] profilePicture;
    private String profilePictureBase64;

    public LeaderBoardEntry( String full_name,  int amount) {
        this.full_name = full_name;
        this.amount = amount;
    }

    public LeaderBoardEntry() {

    }



    public String getName() {
        return full_name;
    }
    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public byte[] getProfilePicture() { return profilePicture; }
    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
        if (profilePicture != null) {
            this.profilePictureBase64 = Base64.getEncoder().encodeToString(profilePicture);
        }
    }

    public String getProfilePictureBase64() { return profilePictureBase64; }
    public void setProfilePictureBase64(String profilePictureBase64) { this.profilePictureBase64 = profilePictureBase64; }
}
