package Tools;

import Engine.Cell;


public class WinCondition {

    public static boolean testWin(Cell lastCell, Position pos){
        return (lastCell.getX()==pos.getX() && lastCell.getY()==pos.getY());
    }
}
