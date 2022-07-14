package Engine;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Hero extends Entity{
    public static void main(String[] args) throws InterruptedException {
        Hero lia = new Hero();
        Monster m1 = new Monster();
        System.out.println(lia.fightMonster(m1));
    }
    private static Random rdm = new Random();
    public int luck, strength, stealth;
    private final int MAX_DEXTERITY, MAX_STAMINA, MAX_LUCK;

    /**
     * A Hero has several stats, such as dexterity, stamina, luck, strength and stealth.
     * The dexterity represent the hero's ability to face monsters.
     * The stamina represent the hero's life.
     * The luck represent the hero's ability to escape from traps.
     * The strength represent the hero's ability to carry item (bag capacity).
     * The stealth represent the hero's ability to avoid a fight when entering a room with a monster.
     */
    public Hero(){
        this.dexterity = (rdm.nextInt(6)+1) + 6;
        MAX_DEXTERITY = this.dexterity;
        this.stamina = (rdm.nextInt(11)+2) + 12 ;
        MAX_STAMINA = this.stamina;
        luck = (rdm.nextInt(6)+1) + 6;
        MAX_LUCK=this.luck;
        strength = (rdm.nextInt(11)+2) + 6;
//        stealth = (rdm.nextInt(16)+3) + 10;
    }
    /*
    Actions
     */
    public boolean fightMonster(Monster m) throws InterruptedException {
        int hScore, mScore;
        while(this.stamina>0 && m.stamina>0){
            hScore = this.dexterity + (rdm.nextInt(11)+2);
            mScore = m.dexterity + (rdm.nextInt(11)+2);
            // Hero has a bigger score than monster
            if (hScore>mScore)
                m.stamina-=2;
            // Monster has a bigger score than hero
            if (hScore<mScore)
                this.stamina-=2;
            TimeUnit.MILLISECONDS.sleep(350);
        }
        if (this.stamina>=0){
            return true;
        } else {
            return false;
        }
    }
    public void escapeTrap(Trap tr){
        int trapScore = rdm.nextInt(11)+2;
        if (trapScore<=this.luck){
            this.luck-=1;
        } else {
            this.stamina-=2;
        }
    }

    /*
    Movements
     */
    public void moveLeft(){

    }
}
