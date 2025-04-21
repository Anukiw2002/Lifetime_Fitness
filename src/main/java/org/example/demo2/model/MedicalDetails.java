package org.example.demo2.model;

public class MedicalDetails {
    private String medical_condition;
    private String takes_medication;
    private boolean chest_pain;
    private boolean back_pain;
    private String bone_joint_problem;
    private boolean blood_pressure;
    private String diabetes;
    private String stress_level;
    private String smoking;
    private String activity_level;
    private String exercise_objectives;
    private String other_conditions;

    public String getMedical_condition() {
        return medical_condition;
    }

    public void setMedical_condition(String medicalCondition) {
        this.medical_condition = medicalCondition;
    }

    public String getTakes_medication() {
        return takes_medication;
    }

    public void setTakes_medication(String takesMedication) {
        this.takes_medication = takesMedication;
    }

    public boolean getChest_pain() {
        return chest_pain;
    }

    public void setChest_pain(boolean chestPain) {
        this.chest_pain = chestPain;
    }

    public boolean getBack_pain() {
        return back_pain;
    }

    public void setBack_pain(boolean backPain) {
        this.back_pain = backPain;
    }

    public String getBone_joint_problem() {
        return bone_joint_problem;
    }

    public void setBone_joint_problem(String boneJointProblem) {
        this.bone_joint_problem = boneJointProblem;
    }

    public boolean getBlood_pressure() {
        return blood_pressure;
    }

    public void setBlood_pressure(boolean bloodPressure) {
        this.blood_pressure = bloodPressure;
    }

    public String getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(String diabetes) {
        this.diabetes = diabetes;
    }

    public String getStress_level() {
        return stress_level;
    }

    public void setStress_level(String stressLevel) {
        this.stress_level = stressLevel;
    }

    public String getSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public String getActivity_level() {
        return activity_level;
    }

    public void setActivity_level(String activityLevel) {
        this.activity_level = activityLevel;
    }

    public String getExercise_objectives() {
        return exercise_objectives;
    }

    public void setExercise_objectives(String exerciseObjectives) {
        this.exercise_objectives = exerciseObjectives;
    }

    public String getOther_conditions() {
        return other_conditions;
    }

    public void setOther_conditions(String otherConditions) {
        this.other_conditions = otherConditions;
    }
}
