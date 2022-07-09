package Engine;

import java.util.Random;

public class Dungeon {

    private final int DEFAULT_SIZE = 5;
    private final Difficulty DEFAULT_DIFFICULTY = Difficulty.NORMAL;
    private Difficulty difficulty;
    private int size;
    private static Random rdm = new Random();
    private Cell[][] grid;

    public static void main(String[] args) {
        Dungeon dng = new Dungeon(10, Difficulty.HARDCORE);
//        generateDungeon(dng);
        Loot testL = new Loot(6,3);
        System.out.println(testL.getRatio());

    }
    public Dungeon(){
        difficulty=DEFAULT_DIFFICULTY;
        size = DEFAULT_SIZE;
        this.grid = new Cell[size][size];
    }
    public Dungeon(int size){
        this.size=size;
        this.difficulty=DEFAULT_DIFFICULTY;
        this.grid = new Cell[size][size];
    }
    public Dungeon(Difficulty difficulty){
        this.size=DEFAULT_SIZE;
        this.difficulty=difficulty;
        this.grid = new Cell[DEFAULT_SIZE][DEFAULT_SIZE];
    }
    public Dungeon(int size, Difficulty difficulty){
        this.difficulty=difficulty;
        this.size=size;
        this.grid = new Cell[size][size];
    }
    public Dungeon(Difficulty difficulty, int size){
        this.difficulty=difficulty;
        this.size=size;
        this.grid = new Cell[size][size];
    }

    public Cell setCellEvent() {
        Difficulty d = this.difficulty;
        int rng = rdm.nextInt(100);
        Cell c = null;
        switch (d) {
            case EASY:
                if (rng < Difficulty.EASY.getPerEmpty()) {
                    c = new Cell(CellTypes.EMPTY);
                } else if (rng < (Difficulty.EASY.getPerEmpty() + Difficulty.EASY.getPerMon())) {
                    c = new Cell(CellTypes.MONSTER);
                } else {
                    c = new Cell(CellTypes.TRAP);
                }
                return c;
            case NORMAL:
                if (rng < Difficulty.NORMAL.getPerEmpty()) {
                    c = new Cell(CellTypes.EMPTY);
                } else if (rng < Difficulty.NORMAL.getPerEmpty() + Difficulty.NORMAL.getPerMon()) {
                    c = new Cell(CellTypes.MONSTER);
                } else {
                    c = new Cell(CellTypes.TRAP);
                }
                return c;
            case HARDCORE:
                if (rng < Difficulty.HARDCORE.getPerEmpty()) {
                    c = new Cell(CellTypes.EMPTY);
                } else if (rng < Difficulty.HARDCORE.getPerEmpty() + Difficulty.HARDCORE.getPerMon()) {
                    c = new Cell(CellTypes.MONSTER);
                } else {
                    c = new Cell(CellTypes.TRAP);
                }
                return c;
            default:{
                return null;
            }
        }
    }

    public static void generateDungeon(Dungeon dng){
        int treas_x = rdm.nextInt(dng.size);
        int treas_y = rdm.nextInt(dng.size);
        System.out.println(treas_x+" "+treas_y);
        for (int y=0;y<dng.size;y++){
            for (int x=0;x<dng.size;x++){
                dng.grid[y][x]=dng.setCellEvent();
                if (x==0 && y==0)
                    dng.grid[y][x]=new Cell(CellTypes.SPAWN);
                if (x==treas_x && y==treas_y)
                    dng.grid[y][x]=new Cell(CellTypes.TREASURE);
                System.out.print(dng.grid[y][x].toString());
            }
            System.out.println("");
        }
    }
    public static int numberLoot(){
        return rdm.nextInt(9)+4;
    }

    public void setSize(int n){
        this.size = n;
    }
    public void setDifficulty(Difficulty d) throws Exception {
        if (d==Difficulty.EASY || d==Difficulty.NORMAL || d==Difficulty.HARDCORE){
            this.difficulty=d;
        } else {
            throw new Exception("Choose a difficulty between EASY, NORMAL or HARDCORE");
        }
    }
    public int getSize(){
        return this.size;
    }
    public Difficulty getDifficulty(){
        return this.difficulty;
    }

    @Override
    public String toString() {
        return "Size : "+this.size+" in difficulty "+this.difficulty.toString();
    }
}
