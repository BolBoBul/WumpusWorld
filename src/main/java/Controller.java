import Engine.*;
import Engine.Cell;
import Tools.*;
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

import static Engine.BoardGame.moveToNextCell;
import static java.lang.String.valueOf;

public class Controller {
    private Pane pane;
    private Stage stage;
    public Scene scene;
    private FXMLLoader loader;

    /**
     * Switches the current scene to the LevelCreation
     * The listeners were taken from https://stackoverflow.com/a/14523434 (ChoiceBox) and from
     * http://www.java2s.com/Code/Java/JavaFX/Slidervaluepropertychangelistener.htm (Slider)
     * @param event
     * @throws IOException
     */
    @FXML
    private void switchToLevelCreation(ActionEvent event) throws IOException {
        pane = loader.load(getClass().getResource("newGame.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches the current scene to show the Treasure Room
     * @param event
     * @throws IOException
     */
    @FXML
    private void switchToTreasure(ActionEvent event) throws IOException {
        pane = loader.load(getClass().getResource("treasureRoom.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches the current scene to the Game History board
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void switchToGameHistory(ActionEvent actionEvent) throws IOException {
        pane = loader.load(getClass().getResource("gameHistory.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Closes the application
     * @param event
     */
    @FXML
    private void leaveGame(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}