package farmmanagement;

import java.io.IOException;
import static javafx.application.Application.launch;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("views/Dashboard.fxml"));
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        double screenWidth = screenBounds.getWidth() * 0.8;
        double screenHeight = screenBounds.getHeight() * 0.8;

        Scene scene = new Scene(root, screenWidth, screenHeight);
        primaryStage.setTitle("Farm Management Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
