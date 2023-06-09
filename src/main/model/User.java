package model;

import org.json.JSONObject;
import persistence.Writable;

// Represent a user with their age, body weight, height, gender and activity level
public class User implements Writable {
    private String name;
    private double age;
    private double bodyWeight;
    private double height;
    private Gender gender;
    private ActiveLevel activeLevel;
    private static int metabolism = 0;
    private static int totalEnergyExpenditure = 0;
    private static int caloriesRemaining = 0;

    public enum Gender { FEMALE, MALE }

    public enum ActiveLevel {
        S, // Sedentary
        L, // Lightly active
        M, // Moderately active
        V, // Very active
        E  // Extra active
    }

    /*
     * EFFECTS:  constructs new client information
     */
    public User(String name,double age1, double bodyweight1, double height1, Gender gender, ActiveLevel al) {
        this.name = name;
        this.age = age1;
        this.bodyWeight = bodyweight1;
        this.height = height1;
        this.gender = gender;
        this.activeLevel = al;
    }

    /*
     * EFFECTS:  calculate client's metabolism based on their age, body weight, height and gender
     */
    public int calculateMetabolism() {
        try {
            switch (gender) {
                case FEMALE:
                    metabolism = (int) (655.1 + (4.35 * bodyWeight) + (4.7 * height) - (4.7 * age));
                    break;
                case MALE:
                    metabolism = (int) (66.47 + (6.24 * bodyWeight) + (12.7 * height) - (6.755 * age));
                    break;
            }
        } catch (NullPointerException e) {
            metabolism = 0;
        }
        return metabolism;
    }

    @SuppressWarnings("methodlength")
    public int calculateTotalEnergyExpenditure() {
        calculateMetabolism();
        try {
            switch (activeLevel) {
                case S:
                    totalEnergyExpenditure = (int) (metabolism * 1.2);
                    break;
                case L:
                    totalEnergyExpenditure = (int) (metabolism * 1.375);
                    break;
                case M:
                    totalEnergyExpenditure = (int) (metabolism * 1.55);
                    break;
                case V:
                    totalEnergyExpenditure = (int) (metabolism * 1.725);
                    break;
                case E:
                    totalEnergyExpenditure = (int) (metabolism * 1.9);
                    break;
            }
        } catch (NullPointerException e) {
            totalEnergyExpenditure = 0;
        }
        //System.out.println("Your Total Daily Energy Expenditure is: " + totalEnergyExpenditure);
        return totalEnergyExpenditure;
    }

    public int calculateCaloriesRemaining(FoodList foodList) {
        caloriesRemaining = calculateTotalEnergyExpenditure() + foodList.getTotalDailyCalories();
        //System.out.println("Your daily calories remaining is: " + caloriesRemaining);
        EventLog.getInstance().logEvent(new Event("Calculated user's total remaining calories: "
                + Integer.toString(caloriesRemaining)));
        return caloriesRemaining;
    }

    public int getTotalEnergyExpenditure() {
        return totalEnergyExpenditure;
    }

    public int getCaloriesRemaining() {
        return caloriesRemaining;
    }

    public String getName() {
        return name;
    }

    public double getAge() {
        return age;
    }

    public double getBodyWeight() {
        return bodyWeight;
    }

    public double getHeight() {
        return height;
    }

    public Gender getGender() {
        return gender;
    }

    public ActiveLevel getActiveLevel() {
        return activeLevel;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("age", age);
        json.put("bodyWeight", bodyWeight);
        json.put("height", height);
        json.put("gender", gender);
        json.put("activeLevel", activeLevel);
        return json;
    }
}


