package Controllers;

import Engine.Difficulty;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class newGameController implements Initializable {
    private Pane pane;
    private Stage stage;
    public Scene scene;
    private FXMLLoader loader;

    @FXML
    ImageView ivNewGame;

    @FXML
    private Slider mapSizeSlider;
    private static int mapSize;
    @FXML
    private ChoiceBox<Difficulty> diffChoiceBox;
    private static Difficulty diffChosen;
    private final ObservableList<Difficulty> diffChoice = FXCollections.observableArrayList(Difficulty.PEACEFUL, Difficulty.EASY, Difficulty.NORMAL, Difficulty.HARD, Difficulty.EXTREME);
    private Image newGameImage = new Image("ImageLibrary"+File.separator+"newGameImage.png");

    /**
     * Bring the user to the main Menu.
     * @param actionEvent A semantic event which indicates that a component-defined action occurred.
     * @throws IOException If the fxml file is not found.
     */
    public void switchToMain(ActionEvent actionEvent) throws IOException {
        pane = FXMLLoader.load(getClass().getResource(".."+ File.separator+"mainMenu.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Bring the user in-game.
     * @param actionEvent A semantic event which indicates that a component-defined action occurred.
     * @throws IOException If the fxml file is not found.
     */
    public void switchToInGame(ActionEvent actionEvent) throws IOException {
        pane = FXMLLoader.load(getClass().getResource(".."+ File.separator+"inGame.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public static Difficulty getDifficulty(){
        return diffChosen;
    }
    public static int getMapSize(){
        return mapSize;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        diffChosen=Difficulty.NORMAL;
        mapSize=5;

        diffChoiceBox.setItems(diffChoice);
        diffChoiceBox.setValue(Difficulty.NORMAL);
        diffChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                diffChosen = diffChoiceBox.getItems().get((Integer) number2);
            }
        });

        mapSizeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                mapSize=new_val.intValue();
            }
        });

        ivNewGame.setImage(newGameImage);
    }
}
