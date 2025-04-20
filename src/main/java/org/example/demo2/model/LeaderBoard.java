package org.example.demo2.model;
import java.util.Base64;

public class LeaderBoard {
    private String name;
    private String email;
    private double startingWeight;
    private double currentWeight;
    private double weightLoss;
    private int rank;
    private int streak;
    private byte[] profilePicture;
    private String profilePictureBase64;

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public double getStartingWeight(){return startingWeight;}
    public void setStartingWeight(double startingWeight){this.startingWeight = startingWeight;}

    public double getCurrentWeight(){return currentWeight;}
    public void setCurrentWeight(double currentWeight){this.currentWeight = currentWeight;}

    public double getWeightLoss(){return weightLoss;}
    public void setWeightLoss(double weightLoss){this.weightLoss = weightLoss;}

    public int getRank(){return rank;}
    public void setRank(int rank){this.rank = rank;}

    public int getStreak(){return streak;}
    public void setStreak(int streak){this.streak = streak;}

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

