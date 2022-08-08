package Engine;

import Tools.AlgoLoot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Loot {
    private double value, weight;
    private final double ratio;
    private final String description;
    private static final Random rdm = new Random();
    private final int MAX_VALUE = 10;
    private final int MAX_WEIGHT = 5;
    private final ArrayList<String> desc = new ArrayList<String>(List.of("Gold ingot", "Jewel", "Statue", "Gemstone"));

    public Loot(){
        value = rdm.nextInt(MAX_VALUE)+1;
        weight = rdm.nextInt(MAX_WEIGHT)+1;
        this.ratio = value/weight;
        description = desc.get(rdm.nextInt(desc.size()));
    }
    public Loot(double value, double weight){
        this.value = value;
        this.weight = weight;
        this.ratio = value/weight;
        description = desc.get(rdm.nextInt(desc.size()));
    }
    public static int numberLoot(){
        return rdm.nextInt(9)+4;
    }

    public static void main(String[] args) {
        ArrayList<Loot> myLoot = new ArrayList<>();
        myLoot.add(new Loot(3,5));
        myLoot.add(new Loot(7,3));
        myLoot.add(new Loot(3,4));
        myLoot.add(new Loot(1,5));
        myLoot.add(new Loot(3,2));

        Object[] a = AlgoLoot.getBestLoot2(myLoot, 12);
        System.out.println(a[0]);
        System.out.println(a[1]);
    }

    public static ArrayList<Loot> generateLoot(){
        ArrayList<Loot> myLoot = new ArrayList<>();
        for (int i=0; i<numberLoot();i++){
            myLoot.add(new Loot());
        }
        return myLoot;
    }
    public double getValue(){
        return this.value;
    }
    public double getWeight(){
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
//        String str = "This "+this.description+" weights "+(int) this.weight+"oz and is worth "+(int) this.value+" ecu(s)";
//        String str = "V:"+(int) this.getValue()+" & W:"+(int) this.getWeight();
        String str = this.getDescription()+": "+(int) this.getValue()+" ecu(s) and "+(int) this.getWeight()+"oz";
        return str;
    }
}
