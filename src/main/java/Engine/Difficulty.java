package Engine;

public enum Difficulty {
    PEACEFUL(90, 0, 10),
    EASY(90, 5, 5),
    NORMAL(70, 15, 15),
    HARDCORE(50, 20, 30),
    EXTREME(30, 25, 45)
    ;
    private int perEmpty, perMon, perTrap;
    private Difficulty(int perEmpty, int perMon, int perTrap){
        this.perEmpty=perEmpty;
        this.perMon=perMon;
        this.perTrap=perTrap;
    }
    public int getPerEmpty(){
        return this.perEmpty;
    }
    public int getPerMon(){
        return this.perMon;
    }
    public int getPerTrap(){
        return this.perTrap;
    }

}
