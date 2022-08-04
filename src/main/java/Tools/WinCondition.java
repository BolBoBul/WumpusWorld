package Tools;

import java.util.ArrayList;

public class WinCondition {

    public static boolean testWin(ArrayList<Position> playerPos, Position treasPos){
        for (Position pos : playerPos){
            if (pos.getX()==treasPos.getX() && pos.getY()== treasPos.getY())
                return true;
        }
        return false;
    }
}
