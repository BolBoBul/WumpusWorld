package Engine;

public enum CellTypes {
    EMPTY("O"),
    MONSTER("M"),
    TRAP("T"),
    TREASURE("X"),
    SPAWN("L"),

    ;
    private String ID;
    private CellTypes(String ID){
        this.ID=ID;
    }
    private String getCellTypes(){
        return this.ID;
    }

    @Override
    public String toString() {
        return this.getCellTypes();
    }
}
