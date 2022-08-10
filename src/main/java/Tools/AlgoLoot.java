package Tools;

import Engine.Loot;

import java.util.ArrayList;

public class AlgoLoot {

    /**
     * This algorithm will fill the bag by putting first the most valuable loots.
     * @param myLoot    An ArrayList containing all the Loot objects generated in the Treasure Room.
     * @param bag_size  The size of the bag (i.e. correspond to the attribute 'Strength' of the Hero).
     * @return the total value of the loot inside the bag
     */
    public static int lootPerValue(ArrayList<Loot> myLoot, int bag_size){
//        ArrayList<Loot> lootSelect = null;
//        Object[] res = new Object[2];
        int lootWorth = 0;
        int totalWeight = 0;
        Loot currentLoot;
        Sorting.descendingPerValue(myLoot);

        for (int i=0; i<myLoot.size();i++){
            currentLoot=myLoot.get(i);
            if (currentLoot.getWeight()+totalWeight > bag_size)
                break;
            lootWorth+=currentLoot.getValue();
            totalWeight+=currentLoot.getWeight();
        }
        return lootWorth;
    }

    /**
     * This algorithm will fill the bag by putting first the lightest loots.
     * @param myLoot    An ArrayList containing all the Loot objects generated in the Treasure Room.
     * @param bag_size  The size of the bag (i.e. correspond to the attribute 'Strength' of the Hero).
     * @return the total value of the loot inside the bag
     */
    public static int lootPerWeight(ArrayList<Loot> myLoot, int bag_size){
        int lootWorth = 0;
        int totalWeight = 0;
        Loot currentLoot;
        Sorting.ascendingPerWeight(myLoot);

        for (int i=0; i<myLoot.size();i++){
            currentLoot=myLoot.get(i);
            if (currentLoot.getWeight()+totalWeight > bag_size)
                break;
            lootWorth+=currentLoot.getValue();
            totalWeight+=currentLoot.getWeight();
        }
        return lootWorth;
    }

    /**
     * This algorithm will fill the bag by putting first the loot with the highest ratio (value over weight).
     * @param myLoot    An ArrayList containing all the Loot objects generated in the Treasure Room.
     * @param bag_size  The size of the bag (i.e. correspond to the attribute 'Strength' of the Hero).
     * @return the total value of the loot inside the bag
     */
    public static int lootPerRatio(ArrayList<Loot> myLoot, int bag_size){
        int lootWorth = 0;
        int totalWeight = 0;
        Loot currentLoot;
        Sorting.descendingPerRatio(myLoot);

        for (int i=0; i<myLoot.size();i++){
            currentLoot=myLoot.get(i);
            if (currentLoot.getWeight()+totalWeight > bag_size)
                break;
            lootWorth+=currentLoot.getValue();
            totalWeight+=currentLoot.getWeight();
        }
        return lootWorth;
    }

    /**
     * This algorithm will select which Loot objects to add in the bag depending on {@link #lootPerValue(ArrayList, int)}
     * @param myLoot    An ArrayList containing all the Loot objects generated in the Treasure Room.
     * @param bag_size  The size of the bag (i.e. correspond to the attribute 'Strength' of the Hero).
     * @return An ArrayList with the Loot objects to select.
     */
    public static ArrayList<Loot> selectedItemPerValue(ArrayList<Loot> myLoot, int bag_size){
        int lootWorth = 0;
        int totalWeight = 0;
        ArrayList<Loot> selectItems =new ArrayList<>();
        Sorting.descendingPerValue(myLoot);
        ArrayList<Loot> myLootClone = (ArrayList<Loot>) myLoot.clone();

        Loot currLoot = myLootClone.get(0);

        for (int i=0; i<myLoot.size();i++){
            currLoot=myLoot.get(i);
            if (currLoot.getWeight()+totalWeight > bag_size)
                break;
            selectItems.add(currLoot);
            totalWeight+=currLoot.getWeight();
        }

//        while ((currLoot.getWeight()+totalWeight<bag_size) && myLootClone.size()>0){
//            lootWorth+=currLoot.getValue();
//            totalWeight+= currLoot.getWeight();
//            selectItems.add(myLootClone.get(0));
//            myLootClone.remove(0);
//            currLoot = myLootClone.get(0);
//        }
        return selectItems;
    }

    /**
     * This algorithm will select which Loot objects to add in the bag depending on {@link #lootPerWeight(ArrayList, int)}
     * @param myLoot    An ArrayList containing all the Loot objects generated in the Treasure Room.
     * @param bag_size  The size of the bag (i.e. correspond to the attribute 'Strength' of the Hero).
     * @return  An ArrayList with the Loot objects to select.
     */
    public static ArrayList<Loot> selectedItemPerWeight(ArrayList<Loot> myLoot, int bag_size){
        int lootWorth = 0;
        int totalWeight = 0;
        ArrayList<Loot> selectItems =new ArrayList<>();
        Sorting.ascendingPerWeight(myLoot);
        ArrayList<Loot> myLootClone = (ArrayList<Loot>) myLoot.clone();

        Loot currLoot = myLootClone.get(0);

        for (int i=0; i<myLoot.size();i++){
            currLoot=myLoot.get(i);
            if (currLoot.getWeight()+totalWeight > bag_size)
                break;
            selectItems.add(currLoot);
            totalWeight+=currLoot.getWeight();
        }

//        while ((currLoot.getWeight()+totalWeight<bag_size) && myLootClone.size()>0){
//            lootWorth+=currLoot.getValue();
//            totalWeight+= currLoot.getWeight();
//            selectItems.add(myLootClone.get(0));
//            myLootClone.remove(0);
//            currLoot = myLootClone.get(0);
//        }
        return selectItems;
    }

    /**
     * This algorithm will select which Loot objects to add in the bag depending on {@link #lootPerRatio(ArrayList, int)}
     * @param myLoot    An ArrayList containing all the Loot objects generated in the Treasure Room.
     * @param bag_size  The size of the bag (i.e. correspond to the attribute 'Strength' of the Hero).
     * @return  An ArrayList with the Loot objects to select.
     */
    public static ArrayList<Loot> selectedItemPerRatio(ArrayList<Loot> myLoot, int bag_size){
        int lootWorth = 0;
        int totalWeight = 0;
        ArrayList<Loot> selectItems = new ArrayList<>();
        Sorting.descendingPerRatio(myLoot);
        ArrayList<Loot> myLootClone = (ArrayList<Loot>) myLoot.clone();

        Loot currLoot = myLootClone.get(0);

        for (int i=0; i<myLoot.size();i++){
            currLoot=myLoot.get(i);
            if (currLoot.getWeight()+totalWeight > bag_size)
                break;
            selectItems.add(currLoot);
            totalWeight+=currLoot.getWeight();
        }
//        while ((currLoot.getWeight()+totalWeight<bag_size) && myLootClone.size()>0){
//            lootWorth+=currLoot.getValue();
//            totalWeight+= currLoot.getWeight();
//            selectItems.add(myLootClone.get(0));
//            myLootClone.remove(0);
//            currLoot = myLootClone.get(0);
//        }
        return selectItems;
    }

    /**
     * Return the max value of the different looting algorithms.
     * If there are 2 or 3 identical max_values, the priority is perRatio, then perValue and finally perWeight.
     * Depending on the max value obtained, it will also return the list of objects to select.
     * @param myLoot    An ArrayList containing all the Loot objects generated in the Treasure Room.
     * @param bag_size  The size of the bag (i.e. correspond to the attribute 'Strength' of the Hero).
     * @return An array with the highest value and the list of items to take.
     */
    public static Object[] getBestLoot(ArrayList<Loot> myLoot, int bag_size){
        Object[] bestLoot = new Object[2];
//        ArrayList<Loot> selectLoot = null;
        int max_Loot=0;
        if (lootPerWeight(myLoot, bag_size)>max_Loot){
            max_Loot = lootPerWeight(myLoot, bag_size);
            bestLoot[0]=max_Loot;
            bestLoot[1] = selectedItemPerWeight(myLoot, bag_size);
        }
        if (lootPerValue(myLoot, bag_size)>max_Loot){
            max_Loot = lootPerValue(myLoot, bag_size);
            bestLoot[0]=max_Loot;
            bestLoot[1]=selectedItemPerValue(myLoot, bag_size);
        }
        if (lootPerRatio(myLoot, bag_size)>max_Loot){
            max_Loot = lootPerRatio(myLoot, bag_size);
            bestLoot[0]=max_Loot;
            bestLoot[1]=selectedItemPerRatio(myLoot, bag_size);
        }
        return bestLoot;
    }
}
