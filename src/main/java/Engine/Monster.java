package Engine;

import java.util.Random;

public class Monster extends Entity{
    private final Random rdm = new Random();

    public Monster(){
        this.dexterity = (rdm.nextInt(11)+2);
        this.stamina = (rdm.nextInt(11)+2);
    }
}
