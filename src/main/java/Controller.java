import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

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