package Tools;

import Engine.Loot;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class CustomGrid {

    public static void addAvailableLoot(GridPane gp, ArrayList<Loot> myloot){
        for (int s=0;s<myloot.size();s++){
            RadioButton rb = new RadioButton(myloot.get(s).toString());
            rb.setFont(Font.font(14));
            rb.setFocusTraversable(false);
            rb.setPadding(new Insets(0,0,0,10));
            gp.add(rb, 0, s);
        }
        gp.setVgap(10);
    }

}
