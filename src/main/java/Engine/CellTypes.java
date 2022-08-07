package Engine;

public enum CellTypes {
    EMPTY("EMPTY","O"),
    MONSTER("MONSTER","M"),
    TRAP("TRAP","T"),
    TREASURE("TREASURE","X"),
    HERO("HERO","H"),

    ;
    private String ID;
    private String symbol;
    CellTypes(String ID, String symbol){
        this.ID=ID;
        this.symbol=symbol;
    }
    private String getCellTypes(){
        return this.ID;
    }

    @Override
    public String toString() {
        return this.ID;
    }
}
