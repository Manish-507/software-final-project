package farmmanagement;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

public class FarmCompositeDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the root node
        TreeItem<String> rootNode = new TreeItem<>("Root");

        // Barn and its components
        TreeItem<String> barnNode = new TreeItem<>("Barn");
        TreeItem<String> livestockAreaNode = new TreeItem<>("Live-stock-area");
        TreeItem<String> cowNode = new TreeItem<>("Cow");
        TreeItem<String> milkStorageNode = new TreeItem<>("Milk-storage");

        livestockAreaNode.getChildren().add(cowNode);
        barnNode.getChildren().addAll(livestockAreaNode, milkStorageNode);

        // Command Center and its components
        TreeItem<String> commandCenterNode = new TreeItem<>("Command-Center");
        TreeItem<String> droneNode = new TreeItem<>("Drone");
        commandCenterNode.getChildren().add(droneNode);

        // Crop
        TreeItem<String> cropNode = new TreeItem<>("Crop");

        // Add all nodes to the root
        rootNode.getChildren().addAll(barnNode, commandCenterNode, cropNode);

        // Expand the tree
        rootNode.setExpanded(true);

        // Create the TreeView
        TreeView<String> treeView = new TreeView<>(rootNode);

        // Set up the stage
        Scene scene = new Scene(treeView, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Farm Hierarchy");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
