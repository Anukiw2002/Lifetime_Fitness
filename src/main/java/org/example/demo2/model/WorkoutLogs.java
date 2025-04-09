package org.example.demo2.model;

public class WorkoutLogs {
    private int log_id;
    private int user_id ;
    private int workout_id;
    private int exercise_id ;
    private int set_number ;
    private Long weight ;
    private int reps;
    private String notes ;


    public int getUser_id(){return user_id;}
    public void setUser_id(int userId){this.user_id=userId;}

    public int getWorkout_id(){return workout_id;}
    public void setWorkout_id(int workoutId){this.workout_id=workoutId;}

    public int getExercise_id(){return exercise_id;}
    public void setExercise_id(int exerciseId){this.exercise_id=exerciseId;}

    public int getSet_number(){return set_number;}
    public void setSet_number(int set_number){this.set_number=set_number;}

    public Long getWeight(){return weight;}
    public void setWeight(Long weight){this.weight=weight;}

    public int getReps(){return reps;}
    public void setReps(int reps){this.reps=reps;}

    public String getNotes(){return notes;}
    public void setNotes(String notes){this.notes=notes;}
}
