package Tools;

import Engine.Dungeon;
import Engine.Loot;

import java.util.ArrayList;

/*
add a list with picked items ?
 */

public class AlgoLoot {
    public static void main(String[] args) {
        final int LOOT_SIZE = Loot.numberLoot();
//        final int LOOT_SIZE = 4;
        final int BAG_SIZE = 15;
        ArrayList<Loot> myLoot = new ArrayList<>();
        for (int i=0; i<LOOT_SIZE; i++){
            myLoot.add(new Loot());
        }
        System.out.println(myLoot);
        System.out.println("Par Valeur : "+valuePerValue(myLoot, BAG_SIZE));
        System.out.println("Par Poids : "+valuePerWeight(myLoot, BAG_SIZE));
        System.out.println("Par Ratio : "+valuePerRatio(myLoot, BAG_SIZE));
        System.out.println(getBestLoot(myLoot, BAG_SIZE)[0] +"\n"+getBestLoot(myLoot, BAG_SIZE)[1]);
    }

    /**
     * This algorithm will fill the bag by putting first the most valuable loots
     * @param myLoot is an Arraylist containing all the loot
     * @param bag_size is the size of the bag
     * @return the total value of the loot inside the bag
     */
    public static int valuePerValue(ArrayList<Loot> myLoot, int bag_size){
        int lootWorth = 0;
        int totalWeight = 0;
        Sorting.descendingPerValue(myLoot);
        ArrayList<Loot> myLootClone = (ArrayList<Loot>) myLoot.clone();
        Loot currLoot = myLootClone.get(0);

        while ((currLoot.getWeight()+totalWeight<bag_size) && myLootClone.size()>0){
            lootWorth+=currLoot.getValue();
            totalWeight+= currLoot.getWeight();
            myLootClone.remove(0);
            currLoot = myLootClone.get(0);
        }
        return lootWorth;
    }

    /**
     * This algorithm will fill the bag by putting first the lightest loots
     * @param myLoot is an Arraylist containing all the loot
     * @param bag_size is the size of the bag
     * @return the total value of the loot inside the bag
     */
    public static int valuePerWeight(ArrayList<Loot> myLoot, int bag_size){
        int lootWorth = 0;
        int totalWeight = 0;
        Sorting.ascendingPerWeight(myLoot);
        ArrayList<Loot> myLootClone = (ArrayList<Loot>) myLoot.clone();
        Loot currLoot = myLootClone.get(0);

        while ((currLoot.getWeight()+totalWeight<bag_size) && myLootClone.size()>0){
            lootWorth+=currLoot.getValue();
            totalWeight+= currLoot.getWeight();
            myLootClone.remove(0);
            currLoot = myLootClone.get(0);
        }
        return lootWorth;
    }

    /**
     * This algorithm will fill the bag by putting first the loot will the highest ratio (value over weight)
     * @param myLoot is an Arraylist containing all the loot
     * @param bag_size is the size of the bag
     * @return the total value of the loot inside the bag
     */
    public static int valuePerRatio(ArrayList<Loot> myLoot, int bag_size){
        int lootWorth = 0;
        int totalWeight = 0;
        Sorting.descendingPerRatio(myLoot);
        ArrayList<Loot> myLootClone = (ArrayList<Loot>) myLoot.clone();
        Loot currLoot = myLootClone.get(0);

        while ((currLoot.getWeight()+totalWeight<bag_size) && myLootClone.size()>0){
            lootWorth+=currLoot.getValue();
            totalWeight+= currLoot.getWeight();
            myLootClone.remove(0);
            currLoot = myLootClone.get(0);
        }
        return lootWorth;
    }

    public static ArrayList<Loot> selectedItemPerValue(ArrayList<Loot> myLoot, int bag_size){
        int lootWorth = 0;
        int totalWeight = 0;
        ArrayList<Loot> selectItems =new ArrayList<>();
        Sorting.descendingPerValue(myLoot);
        ArrayList<Loot> myLootClone = (ArrayList<Loot>) myLoot.clone();
        Loot currLoot = myLootClone.get(0);

        while ((currLoot.getWeight()+totalWeight<bag_size) && myLootClone.size()>0){
            lootWorth+=currLoot.getValue();
            totalWeight+= currLoot.getWeight();
            selectItems.add(myLootClone.get(0));
            myLootClone.remove(0);
            currLoot = myLootClone.get(0);
        }
        return selectItems;
    }
    public static ArrayList<Loot> selectedItemPerWeight(ArrayList<Loot> myLoot, int bag_size){
        int lootWorth = 0;
        int totalWeight = 0;
        ArrayList<Loot> selectItems =new ArrayList<>();
        Sorting.ascendingPerWeight(myLoot);
        ArrayList<Loot> myLootClone = (ArrayList<Loot>) myLoot.clone();
        Loot currLoot = myLootClone.get(0);

        while ((currLoot.getWeight()+totalWeight<bag_size) && myLootClone.size()>0){
            lootWorth+=currLoot.getValue();
            totalWeight+= currLoot.getWeight();
            selectItems.add(myLootClone.get(0));
            myLootClone.remove(0);
            currLoot = myLootClone.get(0);
        }
        return selectItems;
    }
    public static ArrayList<Loot> selectedItemPerRatio(ArrayList<Loot> myLoot, int bag_size){
        int lootWorth = 0;
        int totalWeight = 0;
        ArrayList<Loot> selectItems = new ArrayList<>();
        Sorting.descendingPerRatio(myLoot);
        ArrayList<Loot> myLootClone = (ArrayList<Loot>) myLoot.clone();

        Loot currLoot = myLootClone.get(0);

        while ((currLoot.getWeight()+totalWeight<bag_size) && myLootClone.size()>0){
            lootWorth+=currLoot.getValue();
            totalWeight+= currLoot.getWeight();
            selectItems.add(myLootClone.get(0));
            myLootClone.remove(0);
            currLoot = myLootClone.get(0);
        }
        return selectItems;
    }

    public static Object[] lootByValue(ArrayList<Loot> myLoot, int bag_size){
        Object[] valueLoot = new Object[2];
        //TO DO
        return valueLoot;
    }
    public static Object[] lootByWeight(ArrayList<Loot> myLoot, int bag_size){
        Object[] weightLoot = new Object[2];
        //TO DO
        return weightLoot;
    }
    public static Object[] lootByRatio(ArrayList<Loot> myLoot, int bag_size){
        Object[] ratioLoot = new Object[2];
        //TO DO
        return ratioLoot;
    }


    /**
     * Return the max value of the different looting algorithms.
     * If there are 2 or 3 identical max_values, the priority is perRatio > perValue > perWeight
     * @return an array with the highest value and the list of items to take
     */

    // Make an HashMap<LootingValue, SelectedItems> ?

    public static Object[] getBestLoot(ArrayList<Loot> myLoot, int bag_size){
        Object[] bestLoot = new Object[2];
        ArrayList<Loot> myLootClone = (ArrayList<Loot>) myLoot.clone();
        int max_Loot=0;
        if (valuePerWeight(myLoot, bag_size)>max_Loot){
            max_Loot=valuePerWeight(myLoot, bag_size);
            bestLoot[0]=max_Loot;
            bestLoot[1]=selectedItemPerWeight(myLoot, bag_size);
        }
        if (valuePerValue(myLoot, bag_size)>max_Loot){
            max_Loot=valuePerValue(myLoot, bag_size);
            bestLoot[0]=max_Loot;
            bestLoot[1]=selectedItemPerValue(myLoot, bag_size);
        }
        if (valuePerRatio(myLoot, bag_size)>max_Loot){
            max_Loot=valuePerRatio(myLoot, bag_size);
            bestLoot[0]=max_Loot;
            bestLoot[1]=selectedItemPerRatio(myLoot, bag_size);
        }
        return bestLoot;
    }
}
