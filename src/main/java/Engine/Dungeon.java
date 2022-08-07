package Engine;

import Tools.Position;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import static Engine.BoardGame.playerPos;

public class Dungeon {

    public static ArrayList<Cell> hiddenCells = new ArrayList<>();
    public static LinkedList<Cell> pathCell = new LinkedList<>();
    public static Position objectivePos=new Position();
     final Difficulty DEFAULT_DIFFICULTY = Difficulty.NORMAL;
    protected Difficulty difficulty;
    public int size;
    private static Random rdm = new Random();
    public static BoardGame bg;

    public static void main(String[] args) {
        Dungeon dng = new Dungeon(5, Difficulty.NORMAL);
        generateDungeon(dng);

        for (int y = 0; y< dng.size;y++){
            for (int x=0;x< dng.size;x++){
                System.out.print(dng.bg.grid[y][x].getCT());
            }
            System.out.println("");
        }
    }
    public Dungeon(){
        difficulty=DEFAULT_DIFFICULTY;
        bg=new BoardGame();
        size=this.bg.getSize();
    }
    public Dungeon(int size){
        difficulty=DEFAULT_DIFFICULTY;
        bg=new BoardGame(size);
        this.size=size;
    }
    public Dungeon(int size, Difficulty difficulty){
        this.difficulty=difficulty;
        bg=new BoardGame(size);
        this.size=size;
    }
    public Dungeon(Difficulty difficulty, int size){
        this.difficulty=difficulty;
        bg = new BoardGame(size);
        this.size=size;
        }

    public Cell setCellEvent() {
        Difficulty d = this.difficulty;
        int rng = rdm.nextInt(100);
        Cell c;
        switch (d) {
            case PEACEFUL:
                if (rng < Difficulty.PEACEFUL.getPerEmpty()) {
                    c = new Cell(CellTypes.EMPTY);
                } else if (rng < (Difficulty.PEACEFUL.getPerEmpty() + Difficulty.PEACEFUL.getPerMon())) {
                    c = new Cell(CellTypes.MONSTER);
                } else {
                    c = new Cell(CellTypes.TRAP);
                }
                return c;
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
            case EXTREME:
                if (rng < Difficulty.EXTREME.getPerEmpty()) {
                    c = new Cell(CellTypes.EMPTY);
                } else if (rng < (Difficulty.EXTREME.getPerEmpty() + Difficulty.EXTREME.getPerMon())) {
                    c = new Cell(CellTypes.MONSTER);
                } else {
                    c = new Cell(CellTypes.TRAP);
                }
                return c;
            default:
                return null;
        }
    }

    public static void generateDungeon(Dungeon dng){
        objectivePos = initTreasureLoc(dng);
        int treas_x = objectivePos.getX();
        int treas_y = objectivePos.getY();

        for (int y=0;y<dng.bg.size;y++){
            for (int x=0;x<dng.bg.size;x++){
                dng.bg.grid[y][x]=dng.setCellEvent();
                dng.bg.grid[y][x].pos.setPos(x,y);
                System.out.println(dng.bg.grid[y][x].getPos());
                if (x==treas_x && y==treas_y) {
                    dng.bg.grid[y][x]=new Cell(new Position(x, y) , CellTypes.TREASURE);
                }
                hiddenCells.add(dng.bg.grid[y][x]);
                if (x==0 && y==0){
                    dng.bg.grid[y][x]=new Cell(new Position(x, y) ,CellTypes.HERO);
                    dng.bg.grid[y][x].hiddenState=false;
                    playerPos.add(dng.bg.grid[y][x].getPos());
                    pathCell.add(dng.bg.grid[y][x]);
                }
            }
        }
    }

    public static Position initTreasureLoc(Dungeon dng){
        int treas_x = rdm.nextInt(dng.bg.size);
        int treas_y = rdm.nextInt(dng.bg.size);
        while (treas_x==0 && treas_y==0){
            treas_x = rdm.nextInt(dng.bg.size);
            treas_y = rdm.nextInt(dng.bg.size);
        }
        return new Position(treas_x, treas_y);
    }
    public Difficulty getDifficulty(){
        return this.difficulty;
    }
    public int getSize(){
        return this.bg.getSize();
    }
//    public static Position

    @Override
    public String toString() {
        return "Size : "+this.bg.size+" in difficulty "+this.difficulty.toString();
    }
}
