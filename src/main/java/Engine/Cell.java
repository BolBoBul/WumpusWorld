package Engine;

import Tools.Direction;
import Tools.Position;

public class Cell {

    public Position pos;
    public int x,y;
    public boolean hiddenState=true;
    private final CellTypes DEFAULT_CT = CellTypes.EMPTY;
    public CellTypes ct, prev_ct;



    /*
    Constructor
     */
    public Cell(){
        pos=new Position(0,0);
        x=0; y=0;
        ct=DEFAULT_CT;
        prev_ct = CellTypes.EMPTY;
        hiddenState = true;
    }
    public Cell(int x, int y){
        this.x = x; this.y=y;
        pos=new Position(x,y);
        ct=DEFAULT_CT;
        prev_ct = CellTypes.EMPTY;
        hiddenState=true;
    }
    public Cell(CellTypes ct){
        pos=new Position(0,0);
        x=0; y=0;
        this.ct=ct;
        prev_ct = CellTypes.EMPTY;
        hiddenState = true;
    }
    public Cell(Position pos, CellTypes ct){
        this.pos=pos;
        x = pos.getX();
        y = pos.getY();
        this.ct=ct;
        prev_ct=CellTypes.EMPTY;
        hiddenState = true;
    }

    /*
    Getter and Setter
     */
    public CellTypes getCT(){
        return this.ct;
    }
    public CellTypes getPrevCT(){
        return this.prev_ct;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public Position getPos(){
        return this.pos;
    }
    public Cell getNextCell(Direction dir, BoardGame bg){
        Cell c = null;
        c = bg.grid[this.getY()+dir.getDy()][this.getX()+dir.getDx()];
        return c;
    }
//    public Cell getNextCell(Direction dir){
//        try {
//            Cell c = getCell(this.getPos().nextPos(dir), )
//            return c;
//        } catch (IndexOutOfBoundsException e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//    public Cell getCell(Position pos, BoardGame bg){
//        return bg.grid[pos.getY()][pos.getX()];
//    }
    public boolean isHidden(){
        return hiddenState;
    }

    public void setCellType(CellTypes new_ct){
        this.ct=new_ct;
    }
    public void setPrevCT(CellTypes new_prevCT){
        this.prev_ct=new_prevCT;
    }
    public void setHidden(boolean isHidden){
        this.hiddenState = isHidden;
    }


    @Override
    public String toString() {
//        String str = this.ct.toString();
        String str = this.ct.name();
        return str;
    }
}
