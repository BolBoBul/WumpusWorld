package Engine;

import Tools.Position;
import javafx.geometry.Pos;

public class Cell {

    public Position pos;
    public boolean isHidden=true;
    private final CellTypes DEFAULT_CT = CellTypes.EMPTY;
    private CellTypes ct, prev_ct;

    public static void main(String[] args) {
        Cell vide = new Cell();
        Cell nonvide = new Cell(new Position(0,1) ,CellTypes.MONSTER);
        System.out.println(vide.toString());
        System.out.println(nonvide.toString());
    }


    /*
    Getter and Setter
     */
    public Cell(){
        pos=new Position(0,0);
        this.ct=DEFAULT_CT;
        prev_ct = null;
        boolean isHidden = false;
    }
    public Cell(CellTypes ct){
        pos=new Position(0,0);
        this.ct=ct;
        prev_ct = null;
        boolean isHidden = false;
    }
    public Cell(Position pos, CellTypes ct){
        this.ct=ct;
        prev_ct=null;
        boolean isHidden = false;
    }
    public CellTypes getCT(){
        return this.ct;
    }
    public boolean isHidden(){
        return isHidden;
    }

    public void setCellType(CellTypes new_ct){
        this.ct=new_ct;
    }
    public void setHidden(boolean isHidden){
        this.isHidden = isHidden;
    }

    @Override
    public String toString() {
        String str = this.ct.toString();
//        String str = this.ct.name()+" and is hidden ? "+this.isHidden;
        return str;
    }
}
