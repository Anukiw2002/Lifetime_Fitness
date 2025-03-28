package org.example.demo2.model;

public class MedicalHistory {
    private String userEmail;
    private String medicalCondition;
    private String takeMedication;
    private boolean chestPain;
    private boolean backPain;
    private String boneJointProblem;
    private boolean bloodPressure;
    private String diabetes;
    private String stressLevel;
    private String smoking;
    private String activityLevel;
    private String exerciseObjectives;
    private String otherConditions;

    public MedicalHistory(String userEmail, String medicalCondition, String takeMedication, boolean chestPain,
                          boolean backPain, String boneJointProblem, boolean bloodPressure,
                          String diabetes, String stressLevel, String smoking,
                          String activityLevel, String exerciseObjectives, String otherConditions){

        this.userEmail = userEmail;
        this.medicalCondition = medicalCondition;
        this.takeMedication = takeMedication;
        this.chestPain = chestPain;
        this.backPain = backPain;
        this.boneJointProblem = boneJointProblem;
        this.bloodPressure = bloodPressure;
        this.diabetes = diabetes;
        this.stressLevel = stressLevel;
        this.smoking = smoking;
        this.activityLevel = activityLevel;
        this.exerciseObjectives = exerciseObjectives;
        this.otherConditions = otherConditions;
    }

    public  String getUserEmail() {return userEmail;}
    public void setUserEmail(int userId) {this.userEmail = userEmail;}

    public String getMedicalCondition() {return medicalCondition;}
    public void setMedicalCondition(String medicalCondition) {this.medicalCondition = medicalCondition;}

    public String getTakeMedication() { return takeMedication; }
    public void setTakeMedication(String takesMedication) { this.takeMedication = takeMedication; }

    public boolean isChestPain() { return chestPain; }
    public void setChestPain(boolean chestPain) { this.chestPain = chestPain; }

    public boolean isBackPain() { return backPain; }
    public void setBackPain(boolean backPain) { this.backPain = backPain; }

    public String getBoneJointProblem() { return boneJointProblem; }
    public void setBoneJointProblem(String boneJointProblem) { this.boneJointProblem = boneJointProblem; }

    public boolean isBloodPressure() { return bloodPressure; }
    public void setBloodPressure(boolean bloodPressure) { this.bloodPressure = bloodPressure; }

    public String getDiabetes() { return diabetes; }
    public void setDiabetes(String diabetes) { this.diabetes = diabetes; }

    public String getStressLevel() { return stressLevel; }
    public void setStressLevel(String stressLevel) { this.stressLevel = stressLevel; }

    public String getSmoking() { return smoking; }
    public void setSmoking(String smoking) { this.smoking = smoking; }

    public String getActivityLevel() { return activityLevel; }
    public void setActivityLevel(String activityLevel) { this.activityLevel = activityLevel; }

    public String getExerciseObjectives() { return exerciseObjectives; }
    public void setExerciseObjectives(String exerciseObjectives) { this.exerciseObjectives = exerciseObjectives; }

    public String getOtherConditions() { return otherConditions; }
    public void setOtherConditions(String otherConditions) { this.otherConditions = otherConditions; }
}
