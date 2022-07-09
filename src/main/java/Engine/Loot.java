package Engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Loot {
    private int value, weight;
    private double ratio;
    private String description;
    private static Random rdm = new Random();
    private final int MAX_VALUE = 10;
    private final int MAX_WEIGHT = 5;
    private ArrayList<String> desc = new ArrayList<String>(List.of("gold ingot", "jewel", "gemstone"));

    public Loot(){
        value = rdm.nextInt(MAX_VALUE)+1;
        weight = rdm.nextInt(MAX_WEIGHT)+1;
        ratio = value/weight;
        description = desc.get(rdm.nextInt(desc.size()));
    }

    public static void main(String[] args) {
        for (int i=0;i<10;i++){
            Loot l = new Loot();
            System.out.println(l.toString());
        }
    }
    public int getValue(){
        return this.value;
    }
    public int getWeight(){
        return this.weight;
    }
    public double getRatio(){
        return this.ratio;
    }
    public String getDescription(){
        return this.description;
    }
    public void setValue(int new_value){
        this.value=new_value;
    }
    public void setWeight(int new_weight){
        this.weight=new_weight;
    }

    @Override
    public String toString() {
//        String str = "This "+this.description+" weights "+this.weight+"oz and is worth "+this.value+" ecu(s)";
        String str = "V:"+this.getValue()+" & W:"+this.getWeight();
        return str;
    }
}
