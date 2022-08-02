import Engine.*;
import Engine.Cell;
import Tools.AlgoLoot;
import Tools.CustomGrid;
import Tools.ResourcesBrowser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.String.valueOf;

public class Controller implements Initializable {

    private static ArrayList<String> STEALTH_FAIL=new ArrayList<>(),
            STEALTH_SUCCESS=new ArrayList<>(),
            AGILITY_FAIL=new ArrayList<>(),
            AGILITY_SUCCESS=new ArrayList<>();
    private static HashMap<String, Image> strToIm = new HashMap<>();
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

        strToIm.put("monster", new Image("ImageLibrary"+File.separator+"monsterC.png"));
        strToIm.put("trap", new Image("ImageLibrary"+File.separator+"trapC.png"));
        strToIm.put("hero", new Image("ImageLibrary"+File.separator+"heroC.png"));
        strToIm.put("treasure", new Image("ImageLibrary"+File.separator+"treasureC.png"));
        strToIm.put("empty", new Image("ImageLibrary"+File.separator+"nullC.png"));

        Image monster = new Image("ImageLibrary"+File.separator+"monsterC.png");
        Image trap = new Image("ImageLibrary"+File.separator+"trapC.png");
        Image hero = new Image("ImageLibrary"+File.separator+"heroC.png");
        Image treasure = new Image("ImageLibrary"+File.separator+"treasureC.png");
        Image empty = new Image("ImageLibrary"+File.separator+"nullC.png");
    }
    //We can create a new Hero here since we don't use/see his strength on the exploration phase
    private static final Hero myHero = new Hero();
    private static int bagValue, bagWeight;
    private static  ArrayList <Loot> myLoot;
    private static Difficulty diff = Difficulty.NORMAL;
    private static int mapSize = 5;
    public static Dungeon dng=new Dungeon();

    private Pane pane;

    private Stage stage;
    public Scene scene;
    private FXMLLoader loader;
    @FXML
    ProgressBar dexterityPB, staminaPB, luckPB, bagSizePB;

    private final ObservableList<Difficulty> diffChoice = FXCollections.observableArrayList(Difficulty.PEACEFUL, Difficulty.EASY, Difficulty.NORMAL, Difficulty.HARDCORE, Difficulty.EXTREME);
    @FXML
    Label solutionLabel=new Label(""), stealthLab = new Label("") , agilityLab = new Label("");
    @FXML
    CheckBox solutionButton;

    /*
     * Scene methods
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
     * The listeners were taken from https://stackoverflow.com/a/14523434 (ChoiceBox) and from
     * http://www.java2s.com/Code/Java/JavaFX/Slidervaluepropertychangelistener.htm (Slider)
     * @param event
     * @throws IOException
     */
    @FXML
    private void switchToLevelCreation(ActionEvent event) throws IOException {
        pane = loader.load(getClass().getResource("newGame.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ChoiceBox<Difficulty> diffChoiceBox = (ChoiceBox<Difficulty>) (pane.getChildren().get(3));
        Slider mapSizeSlider = (Slider) (pane.getChildren().get(1));
        diffChoiceBox.setItems(diffChoice);
        diffChoiceBox.setValue(Difficulty.NORMAL);

        diffChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                diff = diffChoiceBox.getItems().get((Integer) number2);
            }
        });
        mapSizeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                mapSize=new_val.intValue();
            }
        });

        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchToInGame(ActionEvent event) throws IOException{
        //We create a new Dungeon every time we enter the exploration phase
        dng = new Dungeon(mapSize, diff);

        pane = loader.load(getClass().getResource("inGame.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stealthLab.setText(valueOf(myHero.stealth));
        agilityLab.setText(valueOf(myHero.agility));

        GridPane mapGrid = ((GridPane)(pane.getChildren().get(0)));
        CustomGrid.generateDungeonGrid(mapGrid, dng, diff, mapSize);

        Pane subPane = ((Pane)(pane.getChildren().get(1)));
        updateProgBar(subPane);

//        myHero.fightMonster(new Monster());

        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void switchToTreasure(ActionEvent event) throws IOException {
        pane = loader.load(getClass().getResource("treasureRoom.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        newLoot();

        GridPane lootAv = ((GridPane) ((Pane) ((StackPane) (pane.getChildren().get(0))).getChildren().get(1)).getChildren().get(3));
        Label bestProfitLabel = ((Label) ((Pane) ((StackPane) (pane.getChildren().get(0))).getChildren().get(1)).getChildren().get(4));
        Label solutionLabel = ((Label) ((Pane) ((StackPane) (pane.getChildren().get(0))).getChildren().get(1)).getChildren().get(6));
        ProgressBar bagSizePB = ((ProgressBar) ((Pane) ((StackPane) (pane.getChildren().get(0))).getChildren().get(1)).getChildren().get(9));
        bagSizePB.setProgress(0);
        TextArea tA = ((TextArea) ((Pane) ((StackPane) (pane.getChildren().get(0))).getChildren().get(1)).getChildren().get(18));
        tA.setText(printLootRules("lootPhase.txt"));
        tA.setFont(Font.font("System", 14));

        CustomGrid.addAvailableLoot(lootAv, myLoot);
        ArrayList<Loot> myLootCloned = (ArrayList<Loot>) myLoot.clone();
        bestProfitLabel.setText("A good potential looting value is "+ AlgoLoot.getBestLoot2(myLootCloned, myHero.strength)[0]);
        bestProfitLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        bestProfitLabel.setWrapText(true);
        solutionLabel.setText("To have a nice treasure, you must take :" + AlgoLoot.getBestLoot2(myLootCloned, myHero.strength)[1]);
        solutionLabel.setFont(Font.font("System", 14));

//        updateBag(event);

        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchToGameHistory(ActionEvent actionEvent) throws IOException {
        pane = loader.load(getClass().getResource("gameHistory.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        TextArea tA = (TextArea) pane.getChildren().get(1);
        tA.setText(printHist());
        tA.setFont(Font.font("System", 12));
        tA.setWrapText(true);

        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
    /*
    InGame methods
     */

//    private void printEventLog(String event){
//        TextArea tA = (TextArea) ((Pane) ((Button) actionEvent.getSource()).getParent()).getChildren().get(12);
//        tA.appendText("Hello You juste clicked the button.\n");
//    }

//    private void updateTextures(GridPane gp){
//        for (int y=0; y<mapSize;y++){
//            for (int x=0; x<mapSize;x++){
//                CellTypes ct = dng.bg.grid[y][x].getCT();
//                ImageView iv = (ImageView) gp.getChildren().get(y*mapSize+x);
//                switch (ct){
//                    case HERO:
//                        iv.setImage();
//                        break;
//                    case TRAP:
//                        iv.setImage();
//                        break;
//                    case MONSTER:
//                        iv.setImage();
//                        break;
//                    case TREASURE:
//                        iv.setImage();
//                        break;
//                    default:
//                        iv.setImage();
//                        break;
//                }
//            }
//
//        }
//    }

    private void updateProgBar(Pane subPane){
        ProgressBar dextPB = (ProgressBar) subPane.getChildren().get(3);
        ProgressBar stamPB = (ProgressBar) subPane.getChildren().get(4);
        ProgressBar luckPB = (ProgressBar) subPane.getChildren().get(5);
        Label dextLab = (Label) subPane.getChildren().get(6);
        Label stamLab = (Label) subPane.getChildren().get(7);
        Label luckLab = (Label) subPane.getChildren().get(8);

        dextPB.setProgress((myHero.dexterity*1.0)/myHero.MAX_DEXTERITY);
        stamPB.setProgress((myHero.stamina*1.0)/myHero.MAX_STAMINA);
        luckPB.setProgress((myHero.luck*1.0)/myHero.MAX_LUCK);
        dextLab.setText(myHero.dexterity+"/"+ myHero.MAX_DEXTERITY);
        stamLab.setText(myHero.stamina+"/"+ myHero.MAX_STAMINA);
        luckLab.setText(myHero.luck+"/"+ myHero.MAX_LUCK);
        dextLab.setTextAlignment(TextAlignment.RIGHT);
        stamLab.setTextAlignment(TextAlignment.RIGHT);
        luckLab.setTextAlignment(TextAlignment.RIGHT);
    }
//    private void updateProgBar(Pane pane){
//        dexterityPB.setProgress((myHero.dexterity*1.0)/myHero.MAX_DEXTERITY);
//        dextLab.setText(myHero.dexterity+"/"+ myHero.MAX_DEXTERITY);
//        staminaPB.setProgress((myHero.stamina*1.0)/myHero.MAX_STAMINA);
//        stamLab.setText(myHero.stamina+"/"+ myHero.MAX_STAMINA);
//        luckPB.setProgress((myHero.luck*1.0)/myHero.MAX_LUCK);
//        luckLab.setText(myHero.luck+"/"+ myHero.MAX_LUCK);
//    }

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
     * Remove the fog of war and then, reveal the content of every undiscovered cell.
     * @param actionEvent
     */
    @FXML
    private void revealMap(ActionEvent actionEvent){
//        for (Cell c : Dungeon.unrevealedCells){
//            c.hiddenState=false;
//        }
//        // TO DO : UPDATE TEXTURES
    }

    @FXML
    private void generateDialog(ActionEvent actionEvent){
        ArrayList<ArrayList> all = new ArrayList<>();
        all.add(STEALTH_FAIL);
        all.add(STEALTH_SUCCESS);
        all.add(AGILITY_FAIL);
        all.add(AGILITY_SUCCESS);

        pane = (Pane) ((Button) actionEvent.getSource()).getParent();
        TextArea tA = (TextArea) pane.getChildren().get(12);
        int index = new Random().nextInt(all.size());
        int subIndex = new Random().nextInt(all.get(index).size());
        String dialog = (String) all.get(index).get(subIndex)+'\n';
        tA.appendText(dialog);
    }

    /*
    Treasure Room's related Methods
     */

    /**
     * Show the solution of an optimal solution
     * @param event
     */
    public void seeLootSolution(ActionEvent event) {
        solutionLabel.setVisible(solutionButton.isSelected());
    }

    /**
     *
     * @param fileName the name of the file to print.
     * @return a String containing the rules of the Looting Phase
     */
    public String printLootRules(String fileName){
        String res="";
        File file = new File(ResourcesBrowser.RESOURCESPATH+fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                res+=line+'\n';
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the content of the bag i.e. update its value and its capacity.
     * /!\ Since the loot array is static, must re launch the app to generate a new loot /!\
     * @param actionEvent
     */
    @FXML
    public void updateBag(ActionEvent actionEvent){
        int index = 0;
        bagValue=0;
        bagWeight=0;
        GridPane gp1 = (GridPane) ((Pane) ((Node) actionEvent.getSource()).getParent()).getChildren().get(3);
        Label bagCapacityLab = (Label) ((Pane) ((Node) actionEvent.getSource()).getParent()).getChildren().get(8);
        Label bagValueLab = (Label) ((Pane) ((Node) actionEvent.getSource()).getParent()).getChildren().get(11);
        ProgressBar bagSizePB = (ProgressBar) ((Pane) ((Node) actionEvent.getSource()).getParent()).getChildren().get(9);

        for (Node cb : gp1.getChildren()){
            CheckBox cb1 = (CheckBox) cb;
            if (cb1.isSelected()){
                bagValue+=myLoot.get(index).getValue();
                bagWeight+=myLoot.get(index).getWeight();
            }
            index++;
        }
        double wOverS = (bagWeight * 1.0)/myHero.strength;
        if (wOverS>1){
            ColorAdjust adjust = new ColorAdjust();
            adjust.setHue(-1);
            // Correspond to RED
            bagSizePB.setEffect(adjust);
            bagSizePB.setProgress(1);
        }
        else {
            ColorAdjust adjust = new ColorAdjust();
            adjust.setHue(0);
            // Correspond to AZURE
            bagSizePB.setEffect(adjust);
            bagSizePB.setProgress(wOverS);
        }

        bagSizePB.setProgress(wOverS);
        bagCapacityLab.setText("Bag Weight : "+(bagWeight)+"/"+ myHero.strength);
        bagValueLab.setText("Bag Value: "+bagValue+" ecu(s)");
    }

    public void escapeWithLoot(ActionEvent actionEvent) throws IOException {
        updateBag(actionEvent);
        if (bagWeight> myHero.strength){
            Alert warnTR = new Alert(Alert.AlertType.ERROR);
            Text alertText = new Text("You can't lift your bag. Try to remove some item(s).");
            alertText.setWrappingWidth(270);
            warnTR.getDialogPane().setContent(alertText);
            warnTR.setHeaderText("Your bag is too heavy");
            warnTR.setTitle("Error");
            Optional<ButtonType> result = warnTR.showAndWait();
        } else {
            double score = Difficulty.getFinalScore(diff, bagValue);
            SimpleDateFormat sdf = new SimpleDateFormat("[dd/MM/yyyy HH:mm:ss]");
            File gameScoreText = new File(ResourcesBrowser.RESOURCESPATH+"gameScoreLog.txt");

            try (FileWriter fw = new FileWriter(gameScoreText, true)){
                fw.write(sdf.format(new Date())+" Dungeon (Diff="+diff+" & Size="+mapSize+") and score: "
                        +String.format("%.2f", score)+'\n');
            } catch (IOException e){
                e.printStackTrace();
            }
            switchToMain(actionEvent);
        }
    }

    /**
     * Pop an alert to ask the user if he really wants to leave the treasure room.
     * If yes, he is redirected to the main menu without getting a score from his loot phase.
     * If no, he stays in the treasure room and can continue looting.
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void warnLeaveTR(ActionEvent actionEvent) throws IOException {
        Alert warnTR = new Alert(Alert.AlertType.CONFIRMATION);
        Text alertText = new Text("Are you sure you want to leave the treasure room with this loot ? " +
                "Leaving the room will delete the current loot.");
        alertText.setWrappingWidth(270);
        warnTR.getDialogPane().setContent(alertText);
        warnTR.setTitle("Leave Treasure Room ?");
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
     * Generate a new set of lootable items
     */
    private static void newLoot(){
        myLoot=Loot.generateLoot();
    }


    /**
     * Read the file "gameScoreLog.txt"
     * @return a String representing the file content. This String will be printed as a game history.
     */
    private static String printHist(){
        String res ="";
        try (BufferedReader br = new BufferedReader(new FileReader(ResourcesBrowser.RESOURCESPATH+"gameScoreLog.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                res+=line+'\n';
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @FXML
    private void leaveGame(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void initProgressBar(){
        ColorAdjust adjust = new ColorAdjust();
        adjust.setHue(0);
        dexterityPB.setEffect(adjust);
        adjust.setHue(0.941);
        staminaPB.setEffect(adjust);
        adjust.setHue(0.471);
        luckPB.setEffect(adjust);
    }


    /*
    Movement Methods
     */
    @FXML
    private void moveUp(){
        System.out.println("Moved Up");
    }
    @FXML
    private void moveDown(){
        System.out.println("Moved Down");
    }
    @FXML
    private void moveLeft(){
        System.out.println("Moved Left");
    }
    @FXML
    private void moveRight(){
        System.out.println("Moved Right");
    }



    /**
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}