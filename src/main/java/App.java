import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class App extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        StackPane root;

        try {
            root = loader.load(getClass().getResource("mainMenu.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("My August Project");
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static ArrayList<File> getSongs(){
        String resourcesPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
                + File.separator + "resources" + File.separator;
        ArrayList<File> songs = new ArrayList<File>();
        File directory = new File(resourcesPath + "MusicLibrary");

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                songs.add(file);
                System.out.println(file.getName());
            }
        }
        System.out.println("Updated");
        // Shuffle the music ArrayList in order to have a random playlist
        Collections.shuffle(songs);
        return songs;
    }
}
