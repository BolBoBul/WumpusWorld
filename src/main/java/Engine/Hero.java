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
    public int luck, strength, stealth, agility;
    public final int MAX_DEXTERITY, MAX_STAMINA, MAX_LUCK;

    /**
     * A Hero has several stats, such as dexterity, stamina, luck, strength and stealth.
     * The dexterity represents the hero's ability to face monsters.
     * The stamina represents the hero's life.
     * The luck represents the hero's ability to escape from traps.
     * The strength represents the hero's ability to carry items.
     * The stealth represents the hero's ability to avoid a fight when entering a room with a monster.
     * The ability represents the hero's ability to avoid trap.
     */
    public Hero(){
        this.dexterity = (rdm.nextInt(6)+1) + 6;
        MAX_DEXTERITY = this.dexterity;
        this.stamina = (rdm.nextInt(11)+2) + 12 ;
        MAX_STAMINA = this.stamina;
        luck = (rdm.nextInt(6)+1) + 6;
        MAX_LUCK=this.luck;
        strength = (rdm.nextInt(11)+2) + 6;
        stealth = rdm.nextInt(13)+8;
        agility = rdm.nextInt(13)+8;
    }

    /*
    Actions
     */
    public boolean fightMonster(Monster m) throws InterruptedException {
        int hScore, mScore;
        while(this.stamina>0 && m.stamina>0){
            hScore = this.dexterity + (rdm.nextInt(11)+2);
            mScore = m.dexterity + (rdm.nextInt(11)+2);
            String log=new String();
            // Hero has a bigger score than monster
            if (hScore>mScore) {
                log = "You have inflicted 2 damages to the monster";
                m.stamina-=2;
            }
            // Monster has a bigger score than hero
            if (hScore<mScore) {
                log = "You took 2 damages from the monster";
                this.stamina-=2;
            }
            System.out.println(log);
            TimeUnit.MILLISECONDS.sleep(1500);
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

    /**
     * Roll a D20 (20-side dice). If the result is equal or lower to the hero stealth,
     *  he sneaks into the room without alerting the room.
     * @return True if the roll is a success, false otherwise.
     */
    public boolean stealthTest(){
        return (this.stealth >= (rdm.nextInt(20)+1));
    }
    /**
     * Roll a D20 (20-side dice). If the result is equal or lower to the hero agility,
     *  he manages to avoid the trap.
     * @return True if the roll is a success, false otherwise.
     */
    public boolean agilityTest(){
        return (this.agility >=(rdm.nextInt(20)+1));
    }

    /*
    Movements
     */

}
