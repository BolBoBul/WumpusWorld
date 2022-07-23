package Engine;

public enum Difficulty {
    PEACEFUL(90, 0, 10,0),
    EASY(85, 10, 5,0.7),
    NORMAL(70, 15, 15,1),
    HARDCORE(50, 20, 30, 1.5),
    EXTREME(30, 25, 45, 2)
    ;
    private final int perEmpty, perMon, perTrap;
    private final double scoreMult;
    Difficulty(int perEmpty, int perMon, int perTrap, double scoreMult){
        this.perEmpty=perEmpty;
        this.perMon=perMon;
        this.perTrap=perTrap;
        this.scoreMult=scoreMult;
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
    public double getScoreMult(){
        return this.scoreMult;
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
    public static double getFinalScore(Difficulty d, int bagValue){
        double score;
        switch (d){
            case PEACEFUL:
                //Nothing to do, score will always be 0.
            case EASY:
                score = d.getScoreMult()*bagValue;
            case NORMAL:
                score = d.getScoreMult()*bagValue;
            case HARDCORE:
                score = d.getScoreMult()*bagValue;
            case EXTREME:
                score = d.getScoreMult()*bagValue;
            default:
                score = bagValue;
        }
        return score;
    }
}
