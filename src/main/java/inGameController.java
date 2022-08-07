import Engine.Cell;
import Engine.*;
import Tools.CustomGrid;
import Tools.Direction;
import Tools.Position;
import Tools.WinCondition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import static Engine.BoardGame.canMoveDir;
import static Engine.BoardGame.playerPos;
import static Engine.Dungeon.*;
import static java.lang.String.valueOf;

public class inGameController implements Initializable {
    private Position treasPos;
    private static Hero myHero=new Hero();
    private static Dungeon dng;
    protected static Difficulty diff = newGameController.getDifficulty();
    protected int mapSize = newGameController.getMapSize();
    private static ArrayList<String>
            STEALTH_FAIL=new ArrayList<>(),
            STEALTH_SUCCESS=new ArrayList<>(),
            AGILITY_FAIL=new ArrayList<>(),
            AGILITY_SUCCESS=new ArrayList<>();
    static {
        STEALTH_FAIL.add("*BOOM* You tripped over a rock and drop something, you woke up the monster.");
        STEALTH_FAIL.add("As you walk into the dark room, you can feel a breeze. The problem? This breeze smells like corpse.");
        STEALTH_FAIL.add("As you push the old door, it starts to emit a shrill noise... You start to hear grunts coming from behind the door.");
        // "Prepare to fight !"
        STEALTH_SUCCESS.add("You tiptoe along the walls and you manage to cross the room without waking the creature.");
        STEALTH_SUCCESS.add("As you enter the room, you see a creature staring at you. Fortunately, the creature seems to be blind.");

        AGILITY_FAIL.add("As you enter the room, a swarm of bats swoops down on you, causing you to lose your balance and fall into a pit.");
        AGILITY_FAIL.add("As you walk into the room, a slab sinks under the weight of your foot, it's a trap!");
        AGILITY_SUCCESS.add("You notice a trip wire on the ground, you step over it.");
    }
    private Pane pane;
    private Stage stage;
    public Scene scene;
    private FXMLLoader loader;

    @FXML
    private TextArea eventLogArea;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label dextLab, stamLab, luckLab, stealthLab, agilityLab;
    @FXML
    private ProgressBar dexterityPB, staminaPB, luckPB;

    /**
     * Updates the progressBar visible in the In-Game Scene.
     */
    private void updateProgBar(){
        dexterityPB.setProgress((myHero.dexterity*1.0)/myHero.MAX_DEXTERITY);
        staminaPB.setProgress((myHero.stamina*1.0)/myHero.MAX_STAMINA);
        luckPB.setProgress((myHero.luck*1.0)/myHero.MAX_LUCK);
        dextLab.setText(myHero.dexterity+"/"+ myHero.MAX_DEXTERITY);
        stamLab.setText(myHero.stamina+"/"+ myHero.MAX_STAMINA);
        luckLab.setText(myHero.luck+"/"+ myHero.MAX_LUCK);
        dextLab.setTextAlignment(TextAlignment.RIGHT);
        stamLab.setTextAlignment(TextAlignment.RIGHT);
        luckLab.setTextAlignment(TextAlignment.RIGHT);
    }

    @FXML
    private void generateDialog(ActionEvent actionEvent){
        ArrayList<ArrayList> all = new ArrayList<>();
        all.add(STEALTH_FAIL);
        all.add(STEALTH_SUCCESS);
        all.add(AGILITY_FAIL);
        all.add(AGILITY_SUCCESS);

        pane = (Pane) ((Button) actionEvent.getSource()).getParent();
        int index = new Random().nextInt(all.size());
        int subIndex = new Random().nextInt(all.get(index).size());
        String dialog = (String) all.get(index).get(subIndex)+'\n';
        eventLogArea.appendText(dialog);
    }

    /**
     * Remove the fog of war and then, reveal the content of every undiscovered cell.
     * @param actionEvent
     */
    @FXML
    private void revealMap(ActionEvent actionEvent){
        Button button = (Button) actionEvent.getSource();
        if (hiddenCells.get(0).hiddenState){
            for (Engine.Cell c : hiddenCells){
                c.hiddenState=false;
            }
            button.setText("Hide Map");
        }
        else {
            for (Cell c : hiddenCells){
                c.hiddenState=true;
            }
            button.setText("Reveal Map");
        }
        updateTextures();
    }

    /**
     * Switches the current scene to the Main Menu
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void switchToMain(ActionEvent actionEvent) throws IOException {
        pane = loader.load(getClass().getResource("mainMenu.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void switchToTreasure(ActionEvent actionEvent) throws IOException {
        pane = loader.load(getClass().getResource("treasureRoom.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Ask the user if he really wants to go quit the exploration and go back to the main menu.
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void warnIG(ActionEvent actionEvent) throws IOException {
        Alert warnTR = new Alert(Alert.AlertType.CONFIRMATION);
        Text alertText = new Text("Are you sure you want to leave the game in progress ? If you do, you won't be able to continue it later");
        alertText.setWrappingWidth(270);
        warnTR.getDialogPane().setContent(alertText);
        warnTR.setTitle("Leave Game ?");
        Optional<ButtonType> result = warnTR.showAndWait();
        if (result.get() == ButtonType.OK) {
            switchToMain(actionEvent);
        }
    }

    /**
     * Update the cell's textures
     */
    private void updateTextures(){
        CustomGrid.upTexture(dng, mapGrid);
    }

    public void moveUp(ActionEvent actionEvent) throws IOException {
        Cell e = pathCell.getLast();
        e.x=e.getPos().getX(); e.y=e.getPos().getY();

        if (canMoveDir(dng.bg, e, Direction.UP)) {
            dng.bg.moveToNextCell(pathCell.getLast(), Direction.UP);
            updateTextures();
            this.updateProgBar();
            Position playerPos = BoardGame.playerPos.getLast();
            if (WinCondition.testWin(playerPos, treasPos)) {
                this.switchToTreasure(actionEvent);
            }
        } else {
            System.out.println("Can't move Up");
        }
    }

    public void moveDown(ActionEvent actionEvent) throws IOException {
        Cell e = pathCell.getLast();
        e.x=e.getPos().getX(); e.y=e.getPos().getY();

        if (canMoveDir(dng.bg, e, Direction.DOWN)){
            dng.bg.moveToNextCell(pathCell.getLast(), Direction.DOWN);
            updateTextures();
            this.updateProgBar();
            e = pathCell.getLast();
            Position playerPos = BoardGame.playerPos.getLast();
            if (WinCondition.testWin(playerPos, treasPos)){
                this.switchToTreasure(actionEvent);
            }
        } else {
            System.out.println("Can't move Down");
        }
    }

    public void moveRight(ActionEvent actionEvent) throws IOException {
        Cell e = bg.getCell(playerPos.getLast());
        e.x=e.getPos().getX(); e.y=e.getPos().getY();

        if (canMoveDir(dng.bg, e, Direction.RIGHT)) {
            dng.bg.moveToNextCell(pathCell.getLast(), Direction.RIGHT);
            updateTextures();
            this.updateProgBar();
            Position playerPos = BoardGame.playerPos.getLast();
            if (WinCondition.testWin(playerPos, treasPos)){
                this.switchToTreasure(actionEvent);
            }
        } else {
            System.out.println("Can't move Right");
        }
    }

    public void moveLeft(ActionEvent actionEvent) throws IOException {
        Cell e = pathCell.getLast();
        e.x=e.getPos().getX(); e.y=e.getPos().getY();

        if (canMoveDir(dng.bg, e, Direction.LEFT)){
            dng.bg.moveToNextCell(pathCell.getLast(), Direction.LEFT);
            updateTextures();
            this.updateProgBar();
            Position playerPos = BoardGame.playerPos.getLast();
            if (WinCondition.testWin(playerPos, treasPos)){
                this.switchToTreasure(actionEvent);
            }
        } else {
            System.out.println("Can't move Left");
        }
    }

    public void checkCellEvent(Cell c) throws InterruptedException, IOException {
        CellTypes ct = c.getCT();
        switch (ct){
            case TREASURE:
                this.switchToTreasure(new ActionEvent());
                //Pop alert :"Congrats you managed to find the treasure room
                break;
            case MONSTER:
                if (myHero.stealthTest()){
                    break;
                } else {
                    myHero.fightMonster(new Monster());
                }
                break;
            case TRAP:
                myHero.escapeTrap();
                break;
            default:
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pathCell.clear();
        playerPos.clear();
        myHero = new Hero();
        dng = new Dungeon(mapSize, diff);
        CustomGrid.generateDungeonGrid(mapGrid, dng, diff, mapSize);

        treasPos = Dungeon.objectivePos;

        updateProgBar();
        stealthLab.setText("Stealth: "+valueOf(myHero.stealth));
        agilityLab.setText("Agility: "+valueOf(myHero.agility));

    }
}
