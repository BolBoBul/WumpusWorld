import Engine.Cell;
import Engine.Difficulty;
import Engine.Dungeon;
import Engine.Hero;
import Tools.CustomGrid;
import Tools.Position;
import Tools.WinCondition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import static Engine.Dungeon.pathCell;
import static java.lang.String.valueOf;

public class inGameController implements Initializable {
    private Position treasPos;
    private static Hero myHero=new Hero();
    private static Dungeon dng=new Dungeon();
    protected static Difficulty diff = newGameController.getDifficulty();
    protected int mapSize = newGameController.getMapSize();
    private static ArrayList<String> STEALTH_FAIL=new ArrayList<>(),
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
        if (Dungeon.hiddenCells.get(0).hiddenState){
            for (Engine.Cell c : Dungeon.hiddenCells){
                c.hiddenState=false;
            }
            button.setText("Hide Map");
        }
        else {
            for (Cell c : Dungeon.hiddenCells){
                c.hiddenState=true;
            }
            button.setText("Reveal Map");
        }
        updateTextures();
    }

    /**
     * Switches the current scene to the Main Menu
     * @param event
     * @throws IOException
     */
    @FXML
    private void switchToMain(ActionEvent event) throws IOException {
        pane = loader.load(getClass().getResource("mainMenu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
            pane = loader.load(getClass().getResource("mainMenu.fxml"));
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Update the cell's textures
     */
    private void updateTextures(){
        CustomGrid.upTexture(dng, mapGrid);
    }

    public void moveUp(ActionEvent actionEvent) {
        System.out.println("Moved Up");
    }

    public void moveDown(ActionEvent actionEvent) {
        System.out.println("Moved Down");
    }

    public void moveRight(ActionEvent actionEvent) {
        System.out.println("Moved Right");
//        Cell c = dng.bg.grid[0][0];
//        Cell d = dng.bg.grid[0][1];
//        System.out.println(c.ct+ " "+c.prev_ct);
//        System.out.println(d.ct+ " "+d.prev_ct);
//        moveToNextCell(c, d);
//        System.out.println(c.ct+ " "+c.prev_ct);
//        System.out.println(d.ct+ " "+d.prev_ct);


//        Dungeon.discoveredCell.add();
        updateTextures();
        System.out.println("Treas: "+treasPos);
        System.out.println(WinCondition.testWin(pathCell.get(pathCell.size()-1), treasPos));
//        System.out.println(treasPos);
        this.updateProgBar();
    }

    public void moveLeft(ActionEvent actionEvent) {
        System.out.println("Moved Left");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myHero = new Hero();
        dng = new Dungeon(mapSize, diff);
        CustomGrid.generateDungeonGrid(mapGrid, dng, diff, mapSize);

        treasPos = Dungeon.objectivePos;

        updateProgBar();
        stealthLab.setText("Stealth: "+valueOf(myHero.stealth));
        agilityLab.setText("Agility: "+valueOf(myHero.agility));

    }
}
