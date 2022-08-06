package Tools;

import Engine.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
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

import static Engine.Dungeon.generateDungeon;

public class CustomGrid {
    static Image monster = new Image("ImageLibrary"+File.separator+"monsterC.png");
    static Image trap = new Image("ImageLibrary"+File.separator+"trapC.png");
    static Image treas = new Image("ImageLibrary"+File.separator+"treasureC.png");
    static Image hero = new Image("ImageLibrary"+File.separator+"heroC.png");
    static Image empty = new Image("ImageLibrary"+File.separator+"nullC.png");
    static Image fog = new Image("ImageLibrary"+File.separator+"hiddenCell.png");

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
        generateDungeon(dng);

        for (int y=0; y<size; y++){
            for (int x=0; x<size;x++) {
                ImageView iv = new ImageView();
                iv.setPreserveRatio(true);
                gp.setAlignment(Pos.CENTER);
                Cell c = dng.bg.grid[y][x];
                CellTypes ct = c.getCT();
                if (c.hiddenState)
                    iv.setImage(fog);
                else {
                    switch (ct) {
                        case HERO:
                            iv.setImage(hero);
                            break;
                        case TRAP:
                            iv.setImage(trap);
                            break;
                        case MONSTER:
                            iv.setImage(monster);
                            break;
                        case TREASURE:
                            iv.setImage(treas);
                            break;
                        case EMPTY:
                            iv.setImage(empty);
                            break;
                    }
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

    public static void upTexture(Dungeon dng, GridPane gp){
        ObservableList<Node> gp_children = gp.getChildren();
        for (int y=0;y<gp.getRowCount();y++){
            for (int x=0;x<gp.getColumnCount();x++){
                Cell c = dng.bg.grid[y][x];
                CellTypes ct = c.getCT();
                ImageView iv = (ImageView) gp_children.get(y*gp.getRowCount()+x);
                if (c.isHidden()) {
                    iv.setImage(fog);
                }
                else {
                    switch (ct) {
                        case HERO:
                            iv.setImage(hero);
                            break;
                        case TRAP:
                            iv.setImage(trap);
                            break;
                        case MONSTER:
                            iv.setImage(monster);
                            break;
                        case EMPTY:
                            iv.setImage(empty);
                            break;
                        case TREASURE:
                            iv.setImage(treas);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }




}
