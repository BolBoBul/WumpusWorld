package Tools;

import Engine.Cell;
import Engine.CellTypes;
import Engine.Dungeon;
import Engine.Loot;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.io.File;
import java.util.ArrayList;

import static Engine.Dungeon.initTreasureLoc;

public class CustomGrid {

    public static void addAvailableLoot(GridPane gp, ArrayList<Loot> myloot){
        for (int s=0;s<myloot.size();s++){
            CheckBox cb = new CheckBox(myloot.get(s).toString());
            cb.setFont(Font.font(14));
            cb.setFocusTraversable(false);
            cb.setPadding(new Insets(0,0,0,10));
            cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {

                }
            });
            gp.add(cb, 0, s);
        }
        gp.setVgap(10);
    }

    public static void generateDungeonGrid(GridPane gp, Dungeon dng){
        Dungeon.generateDungeon(new Dungeon());

        Position treasPos = initTreasureLoc(dng);
        int treas_x = treasPos.getX();
        int treas_y = treasPos.getY();

        for (int y=0;y<dng.bg.size;y++){
            for (int x=0;x<dng.bg.size;x++){
                dng.bg.grid[y][x]=dng.setCellEvent();
                dng.bg.grid[y][x].pos=new Position(x, y);
                if (dng.bg.grid[y][x].getCT()==CellTypes.MONSTER)
                    gp.add(new ImageView(new Image("ImageLibrary"+File.separator+"monster.png")), x, y);
                if (dng.bg.grid[y][x].getCT()==CellTypes.TRAP)
                    gp.add(new ImageView(new Image("ImageLibrary"+File.separator+"trap.png")), x, y);
                if (x==0 && y==0)
                    dng.bg.grid[y][x]=new Cell(new Position(x, y) , CellTypes.HERO);
                if (x==treas_x && y==treas_y)
                    dng.bg.grid[y][x]=new Cell(new Position(x, y) ,CellTypes.TREASURE);
                System.out.print(dng.bg.grid[y][x].toString());
            }
            System.out.println("");
        }
    }

}
