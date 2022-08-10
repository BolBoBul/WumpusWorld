package Controllers;

import Tools.ResourcesBrowser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class gameHistoryController implements Initializable {
    private Image gameHistoryImage = new Image("ImageLibrary"+File.separator+"gameHistoryImage.png");
    @FXML
    ImageView ivGameHisto;
    @FXML
    TextArea gameHistory;

    private Stage stage;
    public Scene scene;
    private Pane pane;

    /**
     * Bring the user to the main Menu.
     * @param event A semantic event which indicates that a component-defined action occurred.
     * @throws IOException If the fxml file is not found.
     */
    @FXML
    private void switchToMain(ActionEvent event) throws IOException {
        pane = FXMLLoader.load(getClass().getResource(".."+ File.separator+"mainMenu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameHistory.setText(treasureRoomController.printHist());
        gameHistory.setFont(Font.font("System", 16));
        gameHistory.setWrapText(true);
        ivGameHisto.setImage(gameHistoryImage);
    }
}
