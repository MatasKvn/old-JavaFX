package main;

public class User {
    private int age;
    private double height;
    private double weight;
    private String name;

    public User(String name, int age, double height, double weight){
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }
}
