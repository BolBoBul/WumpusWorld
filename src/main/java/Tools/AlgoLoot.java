package Tools;

import Engine.Loot;

import java.util.ArrayList;

/*
add a list with picked items ?
 */

public class AlgoLoot {
    public static void main(String[] args) {
//        final int LOOT_SIZE = Dungeon.numberLoot();
        final int LOOT_SIZE = 4;
        final int BAG_SIZE = 15;
        ArrayList<Loot> myLoot = new ArrayList<>();
        for (int i=0; i<LOOT_SIZE; i++){
            myLoot.add(new Loot());
        }
        System.out.println(myLoot);
        System.out.println("Par Valeur : "+selectionPerValue(myLoot, BAG_SIZE));
        System.out.println("Par Poids : "+selectionPerWeight(myLoot, BAG_SIZE));
        System.out.println("Par Ratio : "+selectionPerRatio(myLoot, BAG_SIZE));
//        System.out.println(leastCost_BB(myLoot, BAG_SIZE));

    }

    /**
     * This algorithm will fill the bag by putting first the most valuable loots
     * @param myLoot is an Arraylist containing all the loot
     * @param bag_size is the size of the bag
     * @return the total value of the loot inside the bag
     */
    public static int selectionPerValue(ArrayList<Loot> myLoot, int bag_size){
        int lootWorth = 0;
        int totalWeight = 0;
        Sorting.descendingPerValue(myLoot);
        System.out.println(myLoot);
        ArrayList<Loot> myLootClone = (ArrayList<Loot>) myLoot.clone();
        Loot currLoot = myLootClone.get(0);

        while ((currLoot.getWeight()+totalWeight<bag_size) && myLootClone.size()>0){
            currLoot = myLootClone.get(0);
            lootWorth+=currLoot.getValue();
            totalWeight+= currLoot.getWeight();
            myLootClone.remove(0);
        }
        System.out.println("Space used : "+totalWeight);
        return lootWorth;
    }

    /**
     * This algorithm will fill the bag by putting first the lightest loots
     * @param myLoot is an Arraylist containing all the loot
     * @param bag_size is the size of the bag
     * @return the total value of the loot inside the bag
     */
    public static int selectionPerWeight(ArrayList<Loot> myLoot, int bag_size){
        int lootWorth = 0;
        int totalWeight = 0;
        Sorting.ascendingPerWeight(myLoot);
        System.out.println(myLoot);
        ArrayList<Loot> myLootClone = (ArrayList<Loot>) myLoot.clone();
        Loot currLoot = myLootClone.get(0);

        while ((currLoot.getWeight()+totalWeight<bag_size) && myLootClone.size()>0){
            currLoot = myLootClone.get(0);
            lootWorth+=currLoot.getValue();
            totalWeight+= currLoot.getWeight();
            myLootClone.remove(0);
        }
        System.out.println("Space used : "+totalWeight);
        return lootWorth;
    }

    /**
     * This algorithm will fill the bag by putting first the loot will the highest ratio (value over weight)
     * @param myLoot is an Arraylist containing all the loot
     * @param bag_size is the size of the bag
     * @return the total value of the loot inside the bag
     */
    public static int selectionPerRatio(ArrayList<Loot> myLoot, int bag_size){
        int lootWorth = 0;
        int totalWeight = 0;
        Sorting.descendingRatio(myLoot);
        System.out.println(myLoot);
        ArrayList<Loot> myLootClone = (ArrayList<Loot>) myLoot.clone();
        Loot currLoot = myLootClone.get(0);

        while ((currLoot.getWeight()+totalWeight<bag_size) && myLootClone.size()>0){
            currLoot = myLootClone.get(0);
            lootWorth+=currLoot.getValue();
            totalWeight+= currLoot.getWeight();
            myLootClone.remove(0);
        }
        System.out.println("Space used : "+totalWeight);
        return lootWorth;
    }

    /**
     *
     * https://www.youtube.com/watch?v=yV1d-b_NeK8
     * @param myLoot is an Arraylist containing all the loot
     * @param bag_size is the size of the bag
     * @return the total value of the loot inside the bag
     */
    public static int leastCost_BB(ArrayList<Loot> myLoot, int bag_size){

        return 0;
    }
}
