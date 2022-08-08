import Engine.BoardGame;
import Engine.Cell;
import Tools.Direction;
import org.junit.jupiter.api.Test;

public class Movements {
    @Test public void testMoveRight(){
        BoardGame bg = new BoardGame();
        Cell e = bg.grid[0][0];
        bg.moveToNextCell(e, Direction.RIGHT);
    }
    @Test public void testMoveLeft(){
        BoardGame bg = new BoardGame();
        Cell e = bg.grid[0][0];
        bg.moveToNextCell(e, Direction.LEFT);
    }
    @Test public void testMoveDown(){
        BoardGame bg = new BoardGame();
        Cell e = bg.grid[0][0];
        bg.moveToNextCell(e, Direction.DOWN);
    }
    @Test public void testMoveUp(){
        BoardGame bg = new BoardGame();
        Cell e = bg.grid[0][0];
        bg.moveToNextCell(e, Direction.UP);
    }
}
