import Engine.Difficulty;
import Engine.Hero;
import Engine.Loot;
import Tools.AlgoLoot;
import Tools.CustomGrid;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private String resourcesPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
            + File.separator + "resources" + File.separator;
    private Pane pane;

    private Stage stage;
    public Scene scene;
    private KeyEvent keyEvent;
    private Event event;
    private FXMLLoader loader;
    @FXML
    ProgressBar abilityPB, staminaPB, luckPB;
    @FXML
    ChoiceBox<Difficulty> difficultyChoiceBox;
    private Difficulty[] diffChoice= {Difficulty.EASY, Difficulty.NORMAL, Difficulty.HARDCORE, Difficulty.EXTREME};
    @FXML
    Slider sizeSlider;
    @FXML
    Label solutionLabel=new Label("");
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
//        mp.setAutoPlay(false);
        stage.show();
    }

    @FXML
    private void switchToLevelCreation(ActionEvent event) throws IOException {
        pane = loader.load(getClass().getResource("newGame.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(pane);
//        difficultyChoiceBox.set;
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchToInGame(ActionEvent event) throws IOException{
        pane = loader.load(getClass().getResource("inGame.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void switchToTreasure(ActionEvent event) throws IOException {
        pane = loader.load(getClass().getResource("treasureRoom.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ArrayList <Loot> myLoot = Loot.generateLoot();
        //We can create a new Hero here since we don't use/see his strength on the exploration phase
        Hero myHero = new Hero();
        GridPane lootAv = ((GridPane) ((Pane) ((StackPane) (pane.getChildren().get(0))).getChildren().get(1)).getChildren().get(3));
        Label bestProfitLabel = ((Label) ((Pane) ((StackPane) (pane.getChildren().get(0))).getChildren().get(1)).getChildren().get(4));
        Label solutionLabel = ((Label) ((Pane) ((StackPane) (pane.getChildren().get(0))).getChildren().get(1)).getChildren().get(6));
        CustomGrid.addAvailableLoot(lootAv, myLoot);
        bestProfitLabel.setText("A good potential looting value is "+ AlgoLoot.getBestLoot(myLoot, myHero.strength)[0]);
        bestProfitLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        bestProfitLabel.setWrapText(true);
        solutionLabel.setText("To have a nice treasure, you must take :" + AlgoLoot.getBestLoot(myLoot, myHero.strength)[1]);
        solutionLabel.setFont(Font.font("System", 14));
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public void seeLootSolution(ActionEvent event) {
        if (solutionButton.isSelected()) {
            solutionLabel.setVisible(true);
            System.out.println("Showing solution");
        } else {
            System.out.println("Hiding solution");
            solutionLabel.setVisible(false);
        }
    }

    @FXML
    private void leaveGame(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private Difficulty getDungeonDiff(){
        return difficultyChoiceBox.getValue();
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


    /**
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
//     Need to keep mp static
    public void initialize(URL location, ResourceBundle resources) {
//        try {
//            Pane mainMenu = loader.load(getClass().getResource("mainMenu.fxml"));
//            Pane inGame = loader.load(getClass().getResource("inGame.fxml"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


    }
}