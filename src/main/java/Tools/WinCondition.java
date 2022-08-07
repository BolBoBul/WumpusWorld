package Tools;

import Engine.Cell;


public class WinCondition {

    public static boolean testWin(Position playerPos, Position pos){
        return (playerPos.getX()==pos.getX() && playerPos.getY()==pos.getY());
    }
}
