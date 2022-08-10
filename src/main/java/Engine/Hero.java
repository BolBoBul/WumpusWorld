package Engine;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import Controllers.inGameController;

public class Hero extends Entity{

    private Controllers.inGameController igCont;

    private static final Random rdm = new Random();
    public int luck, strength, stealth, agility;
    public final int MAX_DEXTERITY, MAX_STAMINA, MAX_LUCK;

    /**
     * A Hero has several stats, such as dexterity, stamina, luck, strength and stealth.
     * The dexterity represents the hero's ability to face monsters. Goes from 7 to 12
     * The stamina represents the hero's life. Goes from 14-24
     * The luck represents the hero's ability to escape from traps. Goes from 7-12
     * The strength represents the hero's ability to carry items. Goes from 8-18
     * The stealth represents the hero's ability to avoid a fight when entering a room with a monster. Goes from 6 to 14
     * The ability represents the hero's ability to avoid trap. Goes from 6 to 14
     */
    public Hero(){
        this.dexterity = (rdm.nextInt(6)+1) + 6;
        MAX_DEXTERITY = this.dexterity;
        this.stamina = (rdm.nextInt(11)+2) + 12 ;
        MAX_STAMINA = this.stamina;
        luck = (rdm.nextInt(6)+1) + 6;
        MAX_LUCK=this.luck;
        strength = (rdm.nextInt(11)+2) + 6;
        stealth = rdm.nextInt(9)+6;
        agility = rdm.nextInt(9)+6;
    }

    public void fightMonster(Monster m) throws InterruptedException, IOException {
        int hScore, mScore;
        String log= "";
        while(this.stamina>0 && m.stamina>0){
            hScore = this.dexterity + (rdm.nextInt(11)+2);
            mScore = m.dexterity + (rdm.nextInt(11)+2);
            // Hero has a bigger score than monster
            if (hScore>mScore) {
                log = "You have inflicted 2 damages to the monster";
                m.stamina-=2;
            }
            // Monster has a bigger score than hero
            if (hScore<mScore) {
                //Update ProgressBar
                log = "You took 2 damages from the monster";
                this.stamina-=2;
            }
            System.out.println(log);
            TimeUnit.MILLISECONDS.sleep(400);
        }
        if (this.stamina<1){
            //Pop alert, you're dead => Ask if play again ? generate new map
            Alert warnIG = new Alert(Alert.AlertType.INFORMATION);
            Text alertText = new Text("You've been defeated by a monster. Your adventure stops here!");
            alertText.setWrappingWidth(270);
            warnIG.getDialogPane().setContent(alertText);
            warnIG.setTitle("You're dead");
            Optional<ButtonType> result = warnIG.showAndWait();
            if (result.get() == ButtonType.OK) {
                igCont.switchToMain();
            }
        }
    }

    public void escapeTrap(){
        int trapScore = rdm.nextInt(11)+2;
        if (trapScore<=this.luck){
//            inGameController.addDialog(text);
            System.out.println("You managed to get out easily");
            this.luck-=1;
        } else {
            System.out.println("You had difficulties to get out");
            this.stamina-=2;
        }
    }

    /**
     * Roll a D10 (10-side dice) and add 8 to it. If the result is equal or lower to the hero stealth,
     *  he sneaks into the room without alerting the room.
     * @return True if the roll is a success, false otherwise.
     */
    public boolean stealthTest(){
        return (this.stealth >= ((rdm.nextInt(10)+1)+8));
    }
    /**
     * Roll a D10 (10-side dice) and add 8 to it. If the result is equal or lower to the hero agility,
     *  he manages to avoid the trap.
     * @return True if the roll is a success, false otherwise.
     */
    public boolean agilityTest(){
        return (this.agility >=((rdm.nextInt(10)+1)+8));
    }

}
