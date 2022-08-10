package Controllers;

import Tools.ResourcesBrowser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainMenuController implements Initializable {
    private Image mainMenuImage = new Image("ImageLibrary"+File.separator+"mainMenuImage.png");
    private Image wWWhite = new Image("ImageLibrary"+File.separator+"wumpusworldwhite.png");
    @FXML
    ImageView ivMainMenu, ivWWW;

    private Pane pane;
    private Stage stage;
    public Scene scene;
    private FXMLLoader loader;

    /**
     * Switches the current scene to the LevelCreation
     * The listeners were taken from https://stackoverflow.com/a/14523434 (ChoiceBox) and from
     * http://www.java2s.com/Code/Java/JavaFX/Slidervaluepropertychangelistener.htm (Slider)
     * @param event A semantic event which indicates that a component-defined action occurred.
     * @throws IOException If the fxml file is not found.
     */
    @FXML
    private void switchToLevelCreation(ActionEvent event) throws IOException {
        pane = FXMLLoader.load(getClass().getResource(".."+File.separator+"newGame.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches the current scene to show the Treasure Room
     * @param event A semantic event which indicates that a component-defined action occurred.
     * @throws IOException If the fxml file is not found.
     */
    @FXML
    private void switchToTreasure(ActionEvent event) throws IOException {
        pane = FXMLLoader.load(getClass().getResource(".."+File.separator+"treasureRoom.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches the current scene to the Game History board
     * @param actionEvent A semantic event which indicates that a component-defined action occurred.
     * @throws IOException If the fxml file is not found.
     */
    @FXML
    private void switchToGameHistory(ActionEvent actionEvent) throws IOException {
        pane = FXMLLoader.load(getClass().getResource(".."+File.separator+"gameHistory.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Closes the application
     * @param event A semantic event which indicates that a component-defined action occurred.
     */
    @FXML
    private void leaveGame(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ivMainMenu.setImage(mainMenuImage);
        ivWWW.setImage(wWWhite);
    }
}