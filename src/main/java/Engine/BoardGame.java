package Engine;

import Tools.Direction;
import Tools.Position;

import java.util.LinkedList;

import static Engine.Dungeon.*;

public class BoardGame {
    public static LinkedList<Position> playerPos = new LinkedList<>();
    private final int DEFAULT_SIZE = 5;
    public int size;
    public Cell[][] grid;

    public static void main(String[] args) {
        Dungeon dng = new Dungeon();
        generateDungeon(dng);
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


//    public static void moveToNextCell(Cell c, Cell d){
//        Cell temp = new Cell();
//
//        d.prev_ct=d.ct;
//        d.ct=c.ct;
//        temp.ct=c.ct;
//        c.ct=c.prev_ct;
//        c.prev_ct=temp.ct;
//
//        d.setHidden(false);
//
//        Dungeon.pathCell.add(d);
//        playerPos.add(d.getPos());
//    }
    public void moveToNextCell(Cell c, Direction dir){
        BoardGame bg = this;
        Cell temp = new Cell();
        System.out.println("Pos initiale: "+c.getPos());
        c.x=c.getPos().getX(); c.y=c.getPos().getY();
        System.out.println(c.getX()+" "+c.getY());
        Cell d = c.getNextCell(dir, bg);
        System.out.println("Pos après deplacement: "+d.getPos());

//        pathCell.add(d);
//        playerPos.add(d.getPos());

        System.out.println("AVANT: "+ c.ct+" "+d.ct);
        System.out.println("       "+ c.prev_ct+" "+d.prev_ct);

        d.prev_ct=d.ct;
//        System.out.println("d prev: "+d.prev_ct);
        d.ct=c.ct;
//        System.out.println("d ct: "+d.ct);
        temp.ct=c.ct;
//        System.out.println("temp ct: "+temp.ct);
        c.ct=c.prev_ct;
//        System.out.println("c ct: "+c.ct);
        c.prev_ct=temp.ct;
//        System.out.println("c prev: "+c.prev_ct);

        System.out.println("APRES: "+ c.ct+" "+d.ct);
        System.out.println("       "+ c.prev_ct+" "+d.prev_ct);

        d.hiddenState=false;

        pathCell.add(d);
        playerPos.add(d.getPos());
        hiddenCells.remove(d);
    }

    public Cell getNextCell(Direction dir){
        Cell start = pathCell.getLast();
        System.out.println("Last cell in List (gNC meth) "+start.getCT()+" "+start.getPrevCT());
        try {
            System.out.println(start.getPos()+" (last cell pos in gNC)");
            Cell c = this.grid[start.getY()+dir.getDy()][start.getX()+dir.getDx()];
            System.out.println("Y: "+start.getY()+dir.getDy()+" X:"+start.getX()+dir.getDx());
            System.out.println("Next cell in dir: "+c.getCT()+" "+c.getPrevCT());
            return c;
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return null;
    }
    public static Cell getNextCell(Direction dir, BoardGame bg){
        Cell start = pathCell.getLast();
        System.out.println("Last cell in List (gNC meth) "+start.getCT()+" "+start.getPrevCT());
        try {
            System.out.println(start.getPos()+" (last cell pos in gNC)");
            Cell c = bg.grid[start.getY()+dir.getDy()][start.getX()+dir.getDx()];
            System.out.println("Y: "+start.getY()+" "+dir.getDy()+" X:"+start.getX()+" "+dir.getDx());
            System.out.println("Next cell in dir: "+c.getCT()+" "+c.getPrevCT());
            return c;
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return null;
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
