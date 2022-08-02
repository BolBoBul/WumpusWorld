package Tools;

import Engine.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

import java.io.File;
import java.util.ArrayList;

public class CustomGrid {

    public static void addAvailableLoot(GridPane gp, ArrayList<Loot> myloot){
        for (int s=0;s<myloot.size();s++){
            CheckBox cb = new CheckBox(myloot.get(s).toString());
            cb.setFont(Font.font(14));
            cb.setFocusTraversable(false);
            cb.setPadding(new Insets(0,0,0,5));
            cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {

                }
            });
            gp.add(cb, 0, s);
        }
        gp.setVgap(10);
    }

    public static void generateDungeonGrid(GridPane gp, Dungeon dng, Difficulty d, int size){

        Image monster = new Image("ImageLibrary"+File.separator+"monsterC.png");
        Image trap = new Image("ImageLibrary"+File.separator+"trapC.png");
        Image treas = new Image("ImageLibrary"+File.separator+"treasureC.png");
        Image hero = new Image("ImageLibrary"+File.separator+"heroC.png");
        Image empty = new Image("ImageLibrary"+File.separator+"nullC.png");

        dng.generateDungeon();

        for (int y=0; y<size; y++){
            for (int x=0; x<size;x++) {
                ImageView iv = new ImageView();
                iv.setPreserveRatio(true);
                gp.setAlignment(Pos.CENTER);
                CellTypes ct = dng.bg.grid[y][x].getCT();
                switch (ct) {
                    case HERO:
                        gp.add(new ImageView(hero), x, y);
                        break;
                    case TRAP:
                        gp.add(new ImageView(trap), x, y);
                        break;
                    case MONSTER:
                        gp.add(new ImageView(monster), x, y);
                        break;
                    case TREASURE:
                        gp.add(new ImageView(treas), x, y);
                        break;
                    case EMPTY:
                        gp.add(new ImageView(empty), x, y);
                        break;
                }
                if (x==0){
                    gp.getRowConstraints().add(new RowConstraints(-1, -1,
                            -1, Priority.ALWAYS, VPos.CENTER, false));
                }

                gp.add(iv, x, y);

            }
            gp.getColumnConstraints().add(new ColumnConstraints(-1, -1,
                    -1, Priority.ALWAYS, HPos.CENTER, false));
        }
        for (ColumnConstraints cc : gp.getColumnConstraints()){
            cc.setHgrow(Priority.ALWAYS);
        }
        for (RowConstraints rc : gp.getRowConstraints()){
            rc.setVgrow(Priority.ALWAYS);
        }
    }



}
