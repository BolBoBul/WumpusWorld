package Tools;

import Engine.Loot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Sorting {

    /**
     * This algorithm will sort loot by descending values and, if necessary, by ascending weights
     * @param myArray is an Arraylist containing all the loot in the treasure room
     */
    public static void descendingPerValue(ArrayList<Loot> myArray){
        Collections.sort(myArray, new Comparator<Loot>(){
            @Override
            public int compare(Loot l1, Loot l2) {
                if (l2.getValue() < l1.getValue()){
                    return -1;
                }
                if (l2.getValue() > l1.getValue()){
                    return 1;
                }
                else
                    if (l2.getWeight() > l1.getWeight()){
                    return -1;
                    }
                    if (l2.getWeight() < l1.getWeight()){
                        return 1;
                    }
                    return 0;
            }
        });
    }

    /**
     * This algorithm will sort loot by ascending weights and, if necessary, by descending values
     * @param myArray is an Arraylist containing all the loot in the treasure room
     */
    public static void ascendingPerWeight(ArrayList<Loot> myArray){
        Collections.sort(myArray, new Comparator<Loot>(){
            @Override
            public int compare(Loot l1, Loot l2) {
                if (l2.getWeight() < l1.getWeight()){
                    return 1;
                }
                if (l2.getWeight() > l1.getWeight()){
                    return -1;
                }
                else
                if (l2.getValue() > l1.getValue()){
                    return 1;
                }
                if (l2.getValue() < l1.getValue()){
                    return -1;
                }
                return 0;
            }
        });
    }
    /**
     * This algorithm will sort loot by ratio of each loot value over its weight
     * @param myArray is an Arraylist containing all the loot in the treasure room
     */
    public static void descendingPerRatio(ArrayList<Loot> myArray){
        Collections.sort(myArray, new Comparator<Loot>(){
            @Override
            public int compare(Loot l1, Loot l2) {
//                System.out.println(l2.getRatio()+" "+l1.getRatio());
                if (l2.getRatio() > l1.getRatio()){
                    return 1;
                }
                if (l2.getRatio() < l1.getRatio()){
                    return -1;
                }
                if (l2.getRatio() == l1.getRatio()) {
                    if (l2.getWeight() > l1.getWeight()) {
                        return 1;
                    }
                    if (l2.getWeight() < l1.getWeight()) {
                        return -1;
                    }
                }
                return 0;
            }
        });
    }
}
