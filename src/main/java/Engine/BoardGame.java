package Engine;

import javafx.scene.layout.GridPane;

public class BoardGame extends GridPane {
    private final int DEFAULT_SIZE = 5;
    protected int size;
    Cell[][] grid;
    public BoardGame(){
        super();
        size = DEFAULT_SIZE;
        grid = new Cell[DEFAULT_SIZE][DEFAULT_SIZE];
    }
    public BoardGame(int size){
        grid = new Cell[size][size];
        this.size = size;
    }
    public Cell nextTo(Cell c){

        return  new Cell();
    }

    public int getSize(){
        return this.size;
    }

    public void setSize(int n){
        this.size = n;
    }
}
