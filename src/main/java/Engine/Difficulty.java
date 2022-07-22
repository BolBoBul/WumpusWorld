package Engine;

public enum Difficulty {
    PEACEFUL(90, 0, 10),
    EASY(85, 10, 5),
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

    public String getDescription(){
        String str;
        switch (this){
            case PEACEFUL:
                str = PEACEFUL.getPerEmpty()+"% of empty rooms,\n"
                        + PEACEFUL.getPerMon()+"% to bump into a monster,\n"
                        + PEACEFUL.getPerTrap()+"% to fall on a trap.";
            case EASY:
                str = EASY.getPerEmpty()+"% of empty rooms,\n"
                        + EASY.getPerMon()+"% to bump into a monster,\n"
                        + EASY.getPerTrap()+"% to fall on a trap.";
            case HARDCORE:
                str = HARDCORE.getPerEmpty()+"% of empty rooms,\n"
                        + HARDCORE.getPerMon()+"% to bump into a monster,\n"
                        + HARDCORE.getPerTrap()+"% to fall on a trap.";
            case EXTREME:
                str = EXTREME.getPerEmpty()+"% of empty rooms,\n"
                        + EXTREME.getPerMon()+"% to bump into a monster,\n"
                        + EXTREME.getPerTrap()+"% to fall on a trap.";
            default:
                str = NORMAL.getPerEmpty()+"% of empty rooms,\n"
                        + NORMAL.getPerMon()+"% to bump into a monster,\n"
                        + NORMAL.getPerTrap()+"% to fall on a trap.";
        }
        return str;
    }
}
