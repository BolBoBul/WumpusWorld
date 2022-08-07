package Engine;

import Tools.Position;

import java.util.LinkedList;

public class test {
    public static void main(String[] args) {
        LinkedList<Position> posLin = new LinkedList<>();
        for (int i=0;i<5;i++){
            Position pos = new Position(i,i+1);
            posLin.add(pos);
            System.out.println(pos);
        }
        System.out.println(posLin.get(posLin.size()-1));
    }
}
