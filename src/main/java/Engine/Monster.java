package Engine;

import java.util.Random;

public class Monster extends Entity{
    private Random rdm = new Random();

    public Monster(){
        this.ability = (rdm.nextInt(11)+2);
        this.stamina = (rdm.nextInt(11)+2);
    }
}
