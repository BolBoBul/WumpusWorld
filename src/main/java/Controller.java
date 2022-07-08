import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private String resourcesPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
            + File.separator + "resources" + File.separator;
    private static MediaPlayer mp;
    private int songNumber;
    private FXMLLoader loader;
    private GridPane gp1, gp2;
    private Pane pane;
    private File directory;
    private File[] files;
    private ArrayList<File> songs;
    private int musicNumber;
    private Media media;

    private Stage stage;
    public Scene scene;
    private boolean playing = true;
    private Parent root, oldroot;
    private Node node, children;
    private ObservableList<Node> listChildren;
    private KeyEvent keyEvent;
    private Event event;

    /*
     * Scene methods
     */
    @FXML
    private void switchToMain(ActionEvent event) throws IOException {
        pane = loader.load(getClass().getResource("mainMenu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(pane);
        stage.setScene(scene);
        mp.setAutoPlay(false);
        stage.show();
    }

    @FXML
    private void switchToLevelSelector(ActionEvent event) throws IOException {
        pane = loader.load(getClass().getResource("levelSelector.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(pane);
        stage.setScene(scene);
        mp.setAutoPlay(false);
        stage.show();
    }

    @FXML
    private void switchToSavedLevel(ActionEvent event) throws IOException {
        pane = loader.load(getClass().getResource("savedLevelScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(pane);
        stage.setScene(scene);
//        mp.setAutoPlay(false);
        stage.show();
    }

    @FXML
    private void switchToOptions(ActionEvent event) throws IOException {
        pane = loader.load(getClass().getResource("options.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(pane);
        stage.setScene(scene);
        mp.setAutoPlay(false);
        stage.show();

    }

    @FXML
    private void leaveGame(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * By pressing "ESC" the player can open the in-game menu.
     * Can't fix it :x
     */
//    @FXML
//    public void showIGMenu(ActionEvent event) {
//        root = ((Node) event.getSource()).getParent();
//        ((Node) event.getSource()).setVisible(false);
//        listChildren = root.getChildrenUnmodifiable();
//        children = listChildren.get(0);             //The menu
//        children.setVisible(true);
//    }

    /**
     * Show the commands
     */
//    @FXML
//    private void showHelp(ActionEvent event) {
//        node = (Node) event.getSource();
//        root = node.getParent().getParent().getParent();
//        listChildren = root.getChildrenUnmodifiable();
//        pane1 = (Pane) listChildren.get(0);
//        pane1.setVisible(false);
//        pane2 = (Pane) listChildren.get(1);
//        pane2.setVisible(true);
//
//    }

    /**
     * Hide the commands
     */
    @FXML
    private void hideHelp(ActionEvent event) {
        root = ((Node) event.getSource()).getParent();
        oldroot = root.getParent();
        children = oldroot.getChildrenUnmodifiable().get(0);
        children.setVisible(true);
        root.setVisible(false);
    }

    /**
     * Save the current level and bring back to the main menu
     */
    @FXML
    private void saveAndQuitGame(ActionEvent event) {
        System.out.println("Saved & Quit");
    }

    @FXML
    private void resumeGame(ActionEvent event) {
        oldroot = ((Node) event.getSource()).getParent().getParent().getParent();
        listChildren = oldroot.getChildrenUnmodifiable();
        System.out.print(oldroot.getClass().toString());
        listChildren.get(2).setVisible(true);
        listChildren.get(0).setVisible(false);
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

        ArrayList<File> songs = App.getSongs();
        System.out.println(songs.get(songNumber).getName());

        media = new Media(songs.get(songNumber).toURI().toString());
        mp = new MediaPlayer(media);
        mp.setCycleCount(10000);
//        mp.setMute(true);
        mp.play();

//        mp.setOnEndOfMedia(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Checked");
//                mp.dispose();
//                mp=new MediaPlayer(new Media(musics.get(musicNumber).toURI().toString()));
//                System.out.println("New Media Set");
//                mp.play();
//            }
//        });
    }
}