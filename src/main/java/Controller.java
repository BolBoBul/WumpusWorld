import Engine.Difficulty;
import Engine.Hero;
import Engine.Loot;
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
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //We can create a new Hero here since we don't use/see his strength on the exploration phase
    private static final Hero myHero = new Hero();
    private static int bagValue, bagWeight;
    private static final ArrayList <Loot> myLoot = Loot.generateLoot();
    private static Difficulty diff = Difficulty.NORMAL;
    private static int mapSize = 5;

    private Pane pane;

    private Stage stage;
    public Scene scene;
    private FXMLLoader loader;
    @FXML
    ProgressBar abilityPB, staminaPB, luckPB, bagSizePB;
    private final ObservableList<Difficulty> diffChoice = FXCollections.observableArrayList(Difficulty.PEACEFUL, Difficulty.EASY, Difficulty.NORMAL, Difficulty.HARDCORE, Difficulty.EXTREME);
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

    /**
     *
     * The listener were taken from http://www.java2s.com/Code/Java/JavaFX/Slidervaluepropertychangelistener.htm(Slider),
     * and from https://stackoverflow.com/questions/14522680/javafx-choicebox-events (ChoiceBox)
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
        System.out.println("Difficulty chosen: "+diff.toString());
        System.out.println("Size chosen: "+mapSize);
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
        ProgressBar bagSizePB = ((ProgressBar) ((Pane) ((StackPane) (pane.getChildren().get(0))).getChildren().get(1)).getChildren().get(9));
        bagSizePB.setProgress(0);


        CustomGrid.addAvailableLoot(lootAv, myLoot);
        ArrayList<Loot> myLootCloned = (ArrayList<Loot>) myLoot.clone();
        bestProfitLabel.setText("A good potential looting value is "+ AlgoLoot.getBestLoot(myLootCloned, myHero.strength)[0]);
        bestProfitLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        bestProfitLabel.setWrapText(true);
        solutionLabel.setText("To have a nice treasure, you must take :" + AlgoLoot.getBestLoot(myLootCloned, myHero.strength)[1]);
        solutionLabel.setFont(Font.font("System", 14));

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
//        bagCapacityLab.setText("Bag Capacity : "+(myHero.strength-bagWeight)+"/"+ myHero.strength);
        bagCapacityLab.setText("Bag Capacity : "+(bagWeight)+"/"+ myHero.strength);
        bagValueLab.setText("Bag Value: "+bagValue+" ecu(s)");
    }

    public void escapeWithLoot(ActionEvent actionEvent) throws IOException {
        if (bagWeight> myHero.strength){
            Alert warnTR = new Alert(Alert.AlertType.ERROR);
            Text alertText = new Text("You can't lift your bag. Try to remove some item(s).");
            alertText.setWrappingWidth(270);
            warnTR.getDialogPane().setContent(alertText);
            warnTR.setHeaderText("Your bag is to heavy");
            warnTR.setTitle("Error");
            Optional<ButtonType> result = warnTR.showAndWait();
        } else {
            double score = Difficulty.getFinalScore(diff, bagValue);
            SimpleDateFormat sdf = new SimpleDateFormat("[dd/MM/yyyy HH:mm:ss]");
            try {
                File gameScoreText = new File(ResourcesBrowser.RESOURCESPATH+"gameScoreLog.txt");
                FileWriter fw = new FileWriter(gameScoreText, true);
                System.out.println(gameScoreText.getAbsolutePath());
                fw.write(sdf.format(new Date())+" Dungeon (Diff="+diff+" & Size="+mapSize+") and score: "
                        +String.format("%.2f", score)+'\n');
                fw.close();
            } catch (IOException e){
                e.printStackTrace();
            }
            switchToMain(actionEvent);
        }
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