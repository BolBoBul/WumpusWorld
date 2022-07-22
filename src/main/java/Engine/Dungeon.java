package Engine;

import Tools.Position;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Dungeon {

    private final Difficulty DEFAULT_DIFFICULTY = Difficulty.NORMAL;
    private Difficulty difficulty;
    private static Random rdm = new Random();
    public BoardGame bg;
    public static ArrayList<Cell> discoveredCell = new ArrayList<>();

    public static void main(String[] args) {
        Dungeon dng = new Dungeon(10, Difficulty.NORMAL);
//        generateDungeon(dng);
//        System.out.println(dng.locateHero().toString());
//        Scanner userInput = new Scanner(System.in);
//        while(true) {
//            String input = userInput.nextLine();
//            System.out.println("input is '" + input + "'");
//
//            switch (input){
//                case "l":
//
//            }
//        }
    }
    public Dungeon(){
        difficulty=DEFAULT_DIFFICULTY;
        bg=new BoardGame();
    }
    public Dungeon(int size){
        difficulty=DEFAULT_DIFFICULTY;
        bg=new BoardGame(size);
    }
    public Dungeon(int size, Difficulty difficulty){
        this.difficulty=difficulty;
        bg=new BoardGame(size);
    }
    public Dungeon(Difficulty difficulty, int size){
        this.difficulty=difficulty;
        bg = new BoardGame(size);
        }

    public Cell setCellEvent() {
        Difficulty d = this.difficulty;
        int rng = rdm.nextInt(100);
        Cell c = null;
        switch (d) {
            case PEACEFUL:
                if (rng < Difficulty.PEACEFUL.getPerEmpty()) {
                    c = new Cell(CellTypes.EMPTY);
//                    c.pos=new Position(x, y);
                } else if (rng < (Difficulty.PEACEFUL.getPerEmpty() + Difficulty.PEACEFUL.getPerMon())) {
                    c = new Cell(CellTypes.MONSTER);
                } else {
                    c = new Cell(CellTypes.TRAP);
                }
            case EASY:
                if (rng < Difficulty.EASY.getPerEmpty()) {
                    c = new Cell(CellTypes.EMPTY);
//                    c.pos=new Position(x, y);
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
                } else if (rng < Difficulty.EXTREME.getPerEmpty() + Difficulty.EXTREME.getPerMon()) {
                    c = new Cell(CellTypes.MONSTER);
                } else {
                    c = new Cell(CellTypes.TRAP);
                }
            default:{
                return null;
            }
        }
    }

//    public void setCellEvent(Difficulty diff){
//        Position treasPos = initTreasureLoc(this);
//        int treas_x = treasPos.getX();
//        int treas_y = treasPos.getY();
//
//        for (int y=0; y<this.bg.size;y++){
//            for (int x=0;x<this.bg.size;x++){
//                if (x==0 && y==0)
//                    this.bg.grid[y][x]=new Cell(new Position(x, y) ,CellTypes.HERO);
//                if (x==treas_x && y==treas_y)
//                    this.bg.grid[y][x]=new Cell(new Position(x, y) ,CellTypes.TREASURE);
//                System.out.print(this.bg.grid[y][x].toString());
//            }
//            System.out.println("");
//        }
//    }

    public static void generateDungeon(Dungeon dng){
        Position treasPos = initTreasureLoc(dng);
        int treas_x = treasPos.getX();
        int treas_y = treasPos.getY();

        for (int y=0;y<dng.bg.size;y++){
            for (int x=0;x<dng.bg.size;x++){
                dng.bg.grid[y][x]=dng.setCellEvent();
                dng.bg.grid[y][x].pos=new Position(x, y);
                if (x==0 && y==0)
                    dng.bg.grid[y][x]=new Cell(new Position(x, y) ,CellTypes.HERO);
                if (x==treas_x && y==treas_y)
                    dng.bg.grid[y][x]=new Cell(new Position(x, y) ,CellTypes.TREASURE);
                System.out.print(dng.bg.grid[y][x].toString());
            }
            System.out.println("");
        }
    }

    public Position locateHero() {
        Position heroPos = new Position(0,0);
        int nbrM=0, nbrT=0, nbrE=0;
        for (int y = 0; y < this.bg.size; y++) {
            for (int x = 0; x < this.bg.size; x++) {
                if (this.bg.grid[y][x].getCT()==CellTypes.HERO)
                    heroPos.setPos(x, y);
                if (this.bg.grid[y][x].getCT()==CellTypes.MONSTER)
                    nbrM+=1;
                if (this.bg.grid[y][x].getCT()==CellTypes.TRAP)
                    nbrT+=1;
                if (this.bg.grid[y][x].getCT()==CellTypes.EMPTY)
                    nbrE+=1;
            }
        }
        System.out.println("Nbr of empty: "+nbrE+", nbr of Monster: "+nbrM+", nbr trap: "+nbrT);
        return heroPos;
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

    /*
    A faire, recréer une BoardGame
     */
    public void setSize(int size){

    }
    /*
    A faire, recréer une BoardGame de la diff
     */
    public void setDifficulty(Difficulty d) throws Exception {

    }
    public Difficulty getDifficulty(){
        return this.difficulty;
    }

    @Override
    public String toString() {
        return "Size : "+this.bg.size+" in difficulty "+this.difficulty.toString();
    }
}
