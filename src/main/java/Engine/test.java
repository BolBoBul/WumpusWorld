package Engine;

import Tools.Position;

public class test {
    public static void main(String[] args) {
        Cell d = new Cell(0,1);
        Position pos = new Position(0,1);
        System.out.println(d.getX()==pos.getX() && d.getY()==pos.getY());
    }
}
