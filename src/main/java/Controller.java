import Engine.Difficulty;
import Engine.Hero;
import Engine.Loot;
import Tools.AlgoLoot;
import Tools.CustomGrid;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //We can create a new Hero here since we don't use/see his strength on the exploration phase
    private static Hero myHero = new Hero();
    private int bagValue, bagWeight;

    private static ArrayList <Loot> myLoot = Loot.generateLoot();

    private String resourcesPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
            + File.separator + "resources" + File.separator;
    private Pane pane;

    private Stage stage;
    public Scene scene;
    private FXMLLoader loader;
    @FXML
    ProgressBar abilityPB, staminaPB, luckPB, bagSizePB;
    ChoiceBox<Difficulty> diffChoiceBox = new ChoiceBox<>();
    TextArea diffDesc;
    private final ObservableList<Difficulty> diffChoice = FXCollections.observableArrayList(Difficulty.PEACEFUL, Difficulty.EASY, Difficulty.NORMAL, Difficulty.HARDCORE, Difficulty.EXTREME);
    @FXML
    Slider sizeSlider;
    @FXML
    Label solutionLabel=new Label(""),
            bagCapLabel= new Label("Bag Capacity : "+bagWeight+"/"+ myHero.strength),
            bagValueLab = new Label("Bag Value: "+bagValue+" ecu(s)");
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

    @FXML
    private void switchToLevelCreation(ActionEvent event) throws IOException {
        pane = loader.load(getClass().getResource("newGame.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ChoiceBox<Difficulty> diffChoiceBox = (ChoiceBox<Difficulty>) (pane.getChildren().get(3));
        diffChoiceBox.setItems(diffChoice);
        Difficulty diffChosen = diffChoiceBox.getValue();
//        String diffDescStr = diffChosen.getDescription();
        TextArea diffDescTxt = (TextArea) (pane.getChildren().get(6));
//        diffDescTxt.setText(diffDescStr);
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchToInGame(ActionEvent event) throws IOException{
        pane = loader.load(getClass().getResource("inGame.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // TO DO
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void switchToTreasure(ActionEvent event) throws IOException {
        pane = loader.load(getClass().getResource("treasureRoom.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        GridPane lootAv = ((GridPane) ((Pane) ((StackPane) (pane.getChildren().get(0))).getChildren().get(1)).getChildren().get(3));
        Label bestProfitLabel = ((Label) ((Pane) ((StackPane) (pane.getChildren().get(0))).getChildren().get(1)).getChildren().get(4));
        Label solutionLabel = ((Label) ((Pane) ((StackPane) (pane.getChildren().get(0))).getChildren().get(1)).getChildren().get(6));
        Label bagCapacityLab = ((Label) ((Pane) ((StackPane) (pane.getChildren().get(0))).getChildren().get(1)).getChildren().get(6));
        Label bagValueLab = ((Label) ((Pane) ((StackPane) (pane.getChildren().get(0))).getChildren().get(1)).getChildren().get(6));

        CustomGrid.addAvailableLoot(lootAv, myLoot);
        System.out.println("after creating grid : "+myLoot);
        ArrayList<Loot> myLootCloned = (ArrayList<Loot>) myLoot.clone();
        bestProfitLabel.setText("A good potential looting value is "+ AlgoLoot.getBestLoot(myLootCloned, myHero.strength)[0]);
        bestProfitLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        bestProfitLabel.setWrapText(true);
        solutionLabel.setText("To have a nice treasure, you must take :" + AlgoLoot.getBestLoot(myLootCloned, myHero.strength)[1]);
        solutionLabel.setFont(Font.font("System", 14));

//        int currBagSize = myHero.strength;
//        bagCapacityLab.setText("Bag Capacity : "+currBagSize+"/"+ myHero.strength);

        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
    /*
    Treasure Room's related Methods
     */
    public void seeLootSolution(ActionEvent event) {
        if (solutionButton.isSelected()) {
            solutionLabel.setVisible(true);
        } else {
            solutionLabel.setVisible(false);
        }
    }
    public void updateBag(ActionEvent actionEvent){
        System.out.println("before bag update: "+myLoot);

        int index = 0;
        bagValue=0;
        bagWeight=0;
        GridPane gp1 = (GridPane) ((Pane) ((Node) actionEvent.getSource()).getParent()).getChildren().get(3);
        Label bagCapacityLab = (Label) ((Pane) ((Node) actionEvent.getSource()).getParent()).getChildren().get(8);
        Label bagValueLab = (Label) ((Pane) ((Node) actionEvent.getSource()).getParent()).getChildren().get(11);

        for (Node cb : gp1.getChildren()){
            CheckBox cb1 = (CheckBox) cb;
            if (cb1.isSelected()){
                bagValue+=myLoot.get(index).getValue();
                bagWeight+=myLoot.get(index).getWeight();
                System.out.println(myLoot.get(index).toString());
            }
            index++;
        }
        bagCapacityLab.setText("Bag Capacity : "+(myHero.strength-bagWeight)+"/"+ myHero.strength);
        bagValueLab.setText("Bag Value: "+bagValue+" ecu(s)");

        System.out.println("Bag updated");
        System.out.println("after bag update: "+myLoot);
    }

    public void escapeWithLoot(){
        /*
        if bag.weight is over hero.strength
            pop an alert saying to remove some items of the bag
        else (bag.w is lower or equal to her.s
            write the score in GameScoreLog.txt with the date and Dungeon'perks
            i.e. size and difficulty
         */
        System.out.println("Your bag is worth "+bagValue+" ecus.");
    }

    @FXML
    private void warnLeaveTR(ActionEvent event) throws IOException {
        Alert warnTR = new Alert(Alert.AlertType.CONFIRMATION);
        Text alertText = new Text("Are you sure you want to leave the treasure room with this loot ? " +
                "Leaving the room will delete the current loot.");
        alertText.setWrappingWidth(270);
        warnTR.getDialogPane().setContent(alertText);
        warnTR.setTitle("Leave Treasure Room ?");
        Optional<ButtonType> result = warnTR.showAndWait();
        if (result.get() == ButtonType.OK) {
            pane = loader.load(getClass().getResource("mainMenu.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();
        }
    }






    @FXML
    private void leaveGame(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void initProgressBar(){
        ColorAdjust adjust = new ColorAdjust();
        adjust.setHue(0);
        abilityPB.setEffect(adjust);
        adjust.setHue(0.941);
        staminaPB.setEffect(adjust);
        adjust.setHue(0.471);
        luckPB.setEffect(adjust);
    }

//    public void getDiffDesc(ActionEvent event){
//        Difficulty diffChosen = diffChoiceBox.getValue();
//        diffDesc.setText(diffChosen.getDescription());
//        System.out.println("Show Diff desc");
//    }


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