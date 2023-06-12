package main;

public class UserBMI extends User {

    private double BMI;
    private String BMICategory;

    public UserBMI(String name, int age, double height, double weight) {
        super(name, age, height, weight);
    }

    public void calculateBMI(){
        BMI = super.getWeight()/Math.pow(super.getHeight()/100, 2);
        BMI = Math.round(BMI*100)/100;
    }

    public void calculateBMICategory(){
        if(BMI < 18.5){
            BMICategory = "Underweight";
        } else if (BMI > 18.5 && BMI < 25) {
            BMICategory = "Normal";
        } else if (BMI >= 25 && BMI < 30){
            BMICategory = "Overweight";
        } else {
            BMICategory = "Obese";
        }
    }

    public double getBMI() {
        return BMI;
    }

    public String getBMICategory() {
        return BMICategory;
    }
}
