import Engine.Difficulty;
import Engine.Hero;
import Engine.Loot;
import Tools.AlgoLoot;
import Tools.CustomGrid;
import Tools.ResourcesBrowser;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class treasureRoomController implements Initializable {
    private Pane pane;
    private Stage stage;
    public Scene scene;
    private FXMLLoader loader;
    @FXML
    Label bestProfitLabel, solutionLabel=new Label(""), bagCapLabel=new Label(), bagValueLabel=new Label();
    @FXML
    CheckBox solutionButton;
    @FXML
    GridPane lootAvailable;
    @FXML
    ProgressBar bagSizePB;
    @FXML
    TextArea lootRule;
    private static int bagValue=0, bagWeight=0;
    private static ArrayList<Loot> myLoot;
    private static final Hero myHero = new Hero();
    private static Difficulty diff = Difficulty.NORMAL;
    private static int mapSize = 5;

    /**
     * Show the solution of an optimal solution
     * @param event
     */
    public void seeLootSolution(ActionEvent event) {
        solutionLabel.setVisible(solutionButton.isSelected());
    }

    /**
     * Return the content of the file "lootPhase.txt" as a String that will be used in a TextArea.
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

        for (Node cb : lootAvailable.getChildren()){
            CheckBox cb1 = (CheckBox) cb;
            if (cb1.isSelected()){
                bagValue+=myLoot.get(index).getValue();
                bagWeight+=myLoot.get(index).getWeight();
            }
            index++;
        }
        double wOverS = (bagWeight * 1.0)/myHero.strength;
        ColorAdjust adjust = new ColorAdjust();
        if (wOverS>1){
            adjust.setHue(-1);
            // Correspond to RED
            bagSizePB.setEffect(adjust);
            bagSizePB.setProgress(1);
        }
        else {
            adjust.setHue(0);
            // Correspond to AZURE
            bagSizePB.setEffect(adjust);
            bagSizePB.setProgress(wOverS);
        }

        bagSizePB.setProgress(wOverS);
        bagCapLabel.setText("Bag Weight: "+(bagWeight)+"/"+ myHero.strength);
        bagValueLabel.setText("Bag Value: "+bagValue+" ecu(s)");
    }

    /**
     * It updates your bag and then check if you can leave the treasure room with your bag.
     * @param actionEvent
     * @throws IOException
     */
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
        myLoot= Loot.generateLoot();
    }

    /**
     * Read the file "gameScoreLog.txt"
     * @return a String representing the file content. This String will be printed as a game history.
     */
    protected static String printHist(){
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
    private void switchToMain(ActionEvent event) throws IOException {
        pane = loader.load(getClass().getResource("mainMenu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newLoot();

        lootRule.setText(printLootRules("lootPhase.txt"));
        lootRule.setFont(Font.font("System", 14));

        CustomGrid.addAvailableLoot(lootAvailable, myLoot);
        ArrayList<Loot> myLootCloned = (ArrayList<Loot>) myLoot.clone();
        bestProfitLabel.setText("A good potential looting value is "+ AlgoLoot.getBestLoot2(myLootCloned, myHero.strength)[0]);
        bestProfitLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        bestProfitLabel.setWrapText(true);
        solutionLabel.setText("To have a nice treasure, you must take :" + AlgoLoot.getBestLoot2(myLootCloned, myHero.strength)[1]);
        solutionLabel.setFont(Font.font("System", 14));

        bagCapLabel.setText("Bag Weight: "+(bagWeight)+"/"+ myHero.strength);
        bagValueLabel.setText("Bag Value: 0 ecu");

        bagSizePB.setProgress(0);
    }
}
