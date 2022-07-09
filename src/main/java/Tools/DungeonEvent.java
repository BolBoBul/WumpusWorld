package Tools;

import Engine.Difficulty;
import Engine.Dungeon;

import java.util.Random;

public class DungeonEvent {
    private Random rdn = new Random();
    private int difficulty = 1;
    private static Random rdm = new Random();

    public static void setCellEvent(Difficulty d, Dungeon dgn) throws Exception {
        int rng = rdm.nextInt(100);
        switch (d){
            case EASY :
                if (rng<90)

                break;
            case NORMAL :

                break;
            case HARDCORE :

                break;
            default:
                throw new Exception("Not a difficulty");
        }
    }
}
