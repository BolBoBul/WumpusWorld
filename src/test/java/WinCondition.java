import Tools.Position;
import org.junit.jupiter.api.Test;

public class WinCondition {
    @Test public void testWinCondition(){
        Position playerPos = new Position(0,0);
        Position treasure = new Position(0,0);
        Tools.WinCondition.testWin(playerPos, treasure);
    }
}
