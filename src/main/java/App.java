import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class App extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        Pane root;

        try {
            root = loader.load(getClass().getResource("mainMenu.fxml"));
            Scene scene = new Scene(root, 900, 600);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Hunt the Wumpus");
            primaryStage.getIcons().add(new Image("ImageLibrary"+File.separator+"appicon.png"));
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
