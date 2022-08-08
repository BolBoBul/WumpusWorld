import Engine.Dungeon;
import org.junit.jupiter.api.Test;

public class Generation {
    @Test public void testInitHeroLocation(){
        Dungeon dng = new Dungeon();
        Dungeon.generateDungeon(dng);
    }
}
