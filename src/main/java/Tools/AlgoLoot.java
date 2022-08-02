package Tools;

import Engine.Loot;
import java.util.ArrayList;

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
//        System.out.println(valuePerValue2(myLoot, BAG_SIZE));
        System.out.println("Par Poids : "+valuePerWeight(myLoot, BAG_SIZE));
//        System.out.println(valuePerWeight2(myLoot, BAG_SIZE));
        System.out.println("Par Ratio : "+valuePerRatio(myLoot, BAG_SIZE));
//        System.out.println(valuePerRatio2(myLoot, BAG_SIZE));
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

    public static int lootPerValue2(ArrayList<Loot> myLoot, int bag_size){
        ArrayList<Loot> lootSelect = null;
        Object[] res = new Object[2];
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

    public static int lootPerWeight2(ArrayList<Loot> myLoot, int bag_size){
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
     * This algorithm will fill the bag by putting first the loot with the highest ratio (value over weight)
     * @param myLoot is an Arraylist containing all the loot
     * @param bag_size is the size of the bag
     * @return the total value of the loot inside the bag
     */
    public static int valuePerRatio(ArrayList<Loot> myLoot, int bag_size){
        int lootWorth = 0;
        int totalWeight = 0;
        int index = 0;
        Sorting.descendingPerRatio(myLoot);
        ArrayList<Loot> myLootClone = (ArrayList<Loot>) myLoot.clone();
        Loot currLoot = myLootClone.get(index);

        while ((currLoot.getWeight()+totalWeight<bag_size) && myLootClone.size()>0){
            lootWorth+=currLoot.getValue();
            totalWeight+= currLoot.getWeight();
            myLootClone.remove(0);
            currLoot = myLootClone.get(0);
        }
        return lootWorth;
    }

    public static int lootPerRatio2(ArrayList<Loot> myLoot, int bag_size){
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

    public static Object[] getBestLoot2(ArrayList<Loot> myLoot, int bag_size){
        Object[] bestLoot = new Object[2];
        ArrayList<Loot> selectLoot = null;
        int max_Loot=0;
        if (lootPerWeight2(myLoot, bag_size)>max_Loot){
            max_Loot = lootPerWeight2(myLoot, bag_size);
            bestLoot[0]=max_Loot;
            bestLoot[1] = selectedItemPerWeight(myLoot, bag_size);
        }
        if (lootPerValue2(myLoot, bag_size)>max_Loot){
            max_Loot = lootPerValue2(myLoot, bag_size);
            bestLoot[0]=max_Loot;
            bestLoot[1]=selectedItemPerValue(myLoot, bag_size);
        }
        if (lootPerRatio2(myLoot, bag_size)>max_Loot){
            max_Loot = lootPerRatio2(myLoot, bag_size);
            bestLoot[0]=max_Loot;
            bestLoot[1]=selectedItemPerRatio(myLoot, bag_size);
        }
        return bestLoot;
    }
}
