package Engine;

import Tools.Direction;
import Tools.Position;

public class BoardGame {
    private final int DEFAULT_SIZE = 5;
    public int size;
    public Cell[][] grid;

    public static void main(String[] args) {
        Cell c = new Cell(CellTypes.HERO);
        Cell d = new Cell(CellTypes.MONSTER);
        moveToNextCell(c, d);

        System.out.println(c.prev_ct);
        System.out.println(d.ct);
        System.out.println(d.prev_ct);

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


    // Do it but with Cell c, Direction dir ?
    public Cell getNextCell(Position pos, Direction dir){
        try {
            Cell c = this.grid[pos.getY()+ dir.getDy()][pos.getX()+ dir.getDx()];
            return c;
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void moveToNextCell(Cell c, Cell d){
        d.prev_ct=d.ct;
        d.ct=c.ct;
        c.ct=c.prev_ct;
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
