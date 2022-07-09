package Engine;

import java.util.Random;

public class Hero extends Entity{
    private Random rdm = new Random();
    protected int luck, bag;
    public Hero(){
        this.ability = (rdm.nextInt(6)+1) + 6;
        this.stamina = (rdm.nextInt(11)+2) + 12 ;
        luck = (rdm.nextInt(6)+1) + 6;
        bag = (rdm.nextInt(11)+2) + 6;
    }

}
