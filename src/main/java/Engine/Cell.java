package Engine;

public class Cell {

    private boolean isRevealed;
    private final CellTypes DEFAULT_CT = CellTypes.EMPTY;
    private CellTypes ct;

    public static void main(String[] args) {
        Cell vide = new Cell();
        Cell nonvide = new Cell(CellTypes.MONSTER);
        System.out.println(vide.toString());
        System.out.println(nonvide.toString());


    }

    public Cell(){
        this.ct=DEFAULT_CT;
        boolean isRevealed = false;
    }
    public Cell(CellTypes ct){
        this.ct=ct;
        boolean isRevealed = false;
    }

    @Override
    public String toString() {
        String str = this.ct.toString();
        return str;
    }
}
