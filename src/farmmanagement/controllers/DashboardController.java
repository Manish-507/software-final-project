package farmmanagement.controllers;

import farmmanagement.models.CompositeComponent;
import farmmanagement.models.LeafComponent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class DashboardController implements Initializable {

    @FXML
    private TreeView<String> treeview;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the Root Component when Dashboard is launched
        CompositeComponent rootComponent = initializeRootComponent();
        refreshTree(rootComponent);
    }

    public void refreshTree(CompositeComponent rootComponent) {
        TreeItem<String> rootTreeItem = rootComponent.toTreeItem();
        treeview.setRoot(rootTreeItem);
        rootTreeItem.setExpanded(true);
    }

    public void openAddItemContainerWindow(ActionEvent event) {
        launchChildWindow("/farmmanagement/views/AddItemContainer.fxml", "Add New Item Container", true);
    }

    public void openAddItemWindow(ActionEvent event) {
        launchChildWindow("/farmmanagement/views/AddItem.fxml", "Add New Item", false);
    }

    // HELPER METHODS
    private void launchChildWindow(String fxmlPath, String title, boolean isItemContainerView) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            if (isItemContainerView) {
                // Access AddItemContainer Controller.
            } else {
                // Access AddItem Controller.
            }

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorDialog("Error", "Window Loading Error", "An error occurred while loading the window. Please try again.");
        }
    }

    private void showErrorDialog(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private CompositeComponent initializeRootComponent() {
        CompositeComponent root = new CompositeComponent("Root", 0, 0, 0, 0, 0, 0);
        CompositeComponent barn = new CompositeComponent("Barn", 1000, 10, 20, 50, 60, 30);
        CompositeComponent livestockArea = new CompositeComponent("Live-stock-area", 500, 15, 25, 30, 40, 20);
        LeafComponent cow = new LeafComponent("Cow", 200, 17, 27, 10, 10, 10);
        livestockArea.addComponent(cow);
        barn.addComponent(livestockArea);
        root.addComponent(barn);
        LeafComponent crop = new LeafComponent("Crop", 50, 30, 40, 5, 5, 5);
        root.addComponent(crop);
        return root;
    }
}
