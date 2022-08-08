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

    /**
     * Return the cell at a certain position
     * @param pos   the position of the cell
     * @return      the cell at the designated position
     */
    public Cell getCell(Position pos){
        return this.grid[pos.getY()][pos.getX()];
    }

    /**
     * Move the hero to the next cell
     * @param c     the cell that represents the hero
     * @param dir   the direction he wants to go
     */
    public void moveToNextCell(Cell c, Direction dir){
        BoardGame bg = this;
        Cell temp = new Cell();
        c.x=c.getPos().getX(); c.y=c.getPos().getY();
        Cell d = c.getNextCell(dir, bg);

        d.prev_ct=d.ct;
        d.ct=c.ct;
        temp.ct=c.ct;
        c.ct=c.prev_ct;
        c.prev_ct=temp.ct;

        d.hiddenState=false;

        pathCell.add(d);
        playerPos.add(d.getPos());
        hiddenCells.remove(d);
    }

    /**
     * Checks if the hero can move in a direction
     * @param bg    the board game
     * @param c     the cell which represents the hero
     * @param dir   the direction
     * @return      true if the movement is possible
     */
    public static boolean canMoveDir(BoardGame bg, Cell c, Direction dir){
        int cx = c.getPos().getX(), cxdx = cx+ dir.getDx();
        int cy = c.getPos().getY(), cydy = cy+ dir.getDy();

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
