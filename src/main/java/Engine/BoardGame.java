package Engine;

import Tools.Direction;
import Tools.Position;

import java.util.ArrayList;
import java.util.LinkedList;

import static Engine.Dungeon.generateDungeon;

public class BoardGame {
    public static LinkedList<Position> playerPos = new LinkedList<>();
    private final int DEFAULT_SIZE = 5;
    public int size;
    public Cell[][] grid;

    public static void main(String[] args) {
        Dungeon dng = new Dungeon();
        generateDungeon(dng);
        playerPos.add(new Position(0,0));
        Cell c = dng.bg.grid[0][0];
        Cell d = dng.bg.getNextCell(Direction.RIGHT);
        System.out.println(c.ct+" "+c.prev_ct);
        System.out.println(d.ct+" "+d.prev_ct);
        System.out.println(d.getPos());

        moveToNextCell(c, d);
        System.out.println(c.ct+" "+c.prev_ct);
        System.out.println(d.ct+" "+d.prev_ct);


        for (Position pos : playerPos){
            System.out.println(pos.toString());
        }
    }
    public BoardGame(){
        size = DEFAULT_SIZE;
        grid = new Cell[DEFAULT_SIZE][DEFAULT_SIZE];
    }
    public BoardGame(int size){
        grid = new Cell[size][size];
        this.size = size;
    }
    public int getSize(){
        return this.size;
    }
    public Cell getCell(Position pos){
        return this.grid[pos.getY()][pos.getX()];
    }


    public Cell getNextCell(Direction dir){
        try {
            Position pos = playerPos.get(playerPos.size()-1);
            Cell c = this.grid[pos.getY()+ dir.getDy()][pos.getX()+ dir.getDx()];
            return c;
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void moveToNextCell(Cell c, Cell d){
        Cell temp = new Cell();

        d.prev_ct=d.ct;
        d.ct=c.ct;
        temp.ct=c.ct;
        c.ct=c.prev_ct;
        c.prev_ct=temp.ct;

        d.setHidden(false);

        Dungeon.pathCell.add(d);
        playerPos.add(d.getPos());
    }


    public static boolean canMoveDir(BoardGame bg, Cell c, Direction dir){
        int cx = c.getX(), cxdx = cx+ dir.getDx();
        int cy = c.getY(), cydy = cy+ dir.getDy();

        switch (dir){
            case UP:
            case DOWN:
                if (cydy>=0 && cydy<bg.getSize()) {
                    return true;
                }
                break;
            case LEFT:
            case RIGHT:
                if (cxdx >=0 && cxdx<bg.getSize())
                    return true;
                break;
        }
        return false;
    }


}
