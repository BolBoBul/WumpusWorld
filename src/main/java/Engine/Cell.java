package Engine;

import Tools.Position;
import javafx.geometry.Pos;
import org.checkerframework.checker.units.qual.C;

public class Cell {

    public Position pos;
    public int x,y;
    public boolean hiddenState=true;
    private final CellTypes DEFAULT_CT = CellTypes.EMPTY;
    public CellTypes ct, prev_ct;

    public static void main(String[] args) {
//        Cell vide = new Cell();
//        Cell nonvide = new Cell(new Position(0,1) ,CellTypes.MONSTER);
//        System.out.println(vide.toString());
//        System.out.println(nonvide.toString());
        Cell c = new Cell(0,1);
        System.out.println(c.getX()+" "+c.getY());
    }


    /*
    Getter and Setter
     */
    public Cell(){
        pos=new Position(0,0);
        ct=DEFAULT_CT;
        prev_ct = CellTypes.EMPTY;
        hiddenState = true;
    }
    public Cell(int x, int y){
        this.x = x;
        this.y=y;
        pos=new Position(x,y);
        ct=DEFAULT_CT;
        prev_ct = CellTypes.EMPTY;
        hiddenState=true;
    }
    public Cell(CellTypes ct){
        pos=new Position(0,0);
        this.ct=ct;
        prev_ct = CellTypes.EMPTY;
        hiddenState = true;
    }
    public Cell(Position pos, CellTypes ct){
        x = pos.getX();
        y = pos.getY();
        this.ct=ct;
        prev_ct=CellTypes.EMPTY;
        hiddenState = true;
    }
    public CellTypes getCT(){
        return this.ct;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public Position getPos(){
        Position pos = new Position(this.x, this.y);
        return pos;
    }
    public boolean isHidden(){
        return hiddenState;
    }

    public void setCellType(CellTypes new_ct){
        this.ct=new_ct;
    }
    public void setHidden(boolean isHidden){
        this.hiddenState = isHidden;
    }

    @Override
    public String toString() {
        String str = this.ct.toString();
//        String str = this.ct.name()+" and is hidden ? "+this.isHidden;
        return str;
    }
}
