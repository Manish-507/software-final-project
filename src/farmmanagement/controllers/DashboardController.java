package farmmanagement.controllers;

import farmmanagement.models.CompositeComponent;
import farmmanagement.models.FarmComponent;
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
    private CompositeComponent rootComponent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the Root Component when Dashboard is launched
        this.rootComponent = initializeRootComponent();
        refreshTree(rootComponent);
    }

    public void refreshTree(CompositeComponent rootComponent) {
        if (rootComponent == null) {
            throw new IllegalArgumentException("Root component cannot be null.");
        }
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

    @FXML
    private void handleDeleteButtonAction() {
        TreeItem<String> selectedTreeItem = treeview.getSelectionModel().getSelectedItem();

        if (selectedTreeItem == null) {
            System.out.println("No item selected for deletion.");
            return;
        }
        if ("Root".equals(selectedTreeItem.getValue())) {
            showErrorDialog("Error", "Cannot Delete Container", "Selected Item Container cannot be deleted since it is the ROOT of the Farm.");
            return;
        }
        String selectedName = selectedTreeItem.getValue();
        if (deleteComponent(rootComponent, selectedName)) {
            refreshTree(rootComponent);
            System.out.println("Deleted node: " + selectedName);
        } else {
            System.out.println("Failed to delete node: " + selectedName);
        }
    }

    // HELPER METHODS
    private void launchChildWindow(String fxmlPath, String title, boolean isItemContainerView) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            if (isItemContainerView) {
                AddItemContainerController controller = loader.getController();
                controller.setCompositeComponent(rootComponent);
                controller.populateItemContainerDropdown();
                controller.setDashboardController(this);
            } else {
                AddItemController controller = loader.getController();
                controller.setCompositeComponent(rootComponent);
                controller.populateItemContainerDropdown();
                controller.setDashboardController(this);
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

    private boolean deleteComponent(CompositeComponent parent, String nameToDelete) {
        for (FarmComponent child : parent.getChildren()) {
            if (child.getName().equals(nameToDelete)) {
                parent.removeComponent(child);
                return true;
            }
            if (child instanceof CompositeComponent) {
                boolean deleted = deleteComponent((CompositeComponent) child, nameToDelete);
                if (deleted) {
                    return true;
                }
            }
        }
        return false;
    }

    private void showErrorDialog(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private CompositeComponent initializeRootComponent() {
        CompositeComponent root = new CompositeComponent("Root", 0, 0, 0, 1, 800, 800);
        CompositeComponent barn = new CompositeComponent("Barn", 1000, 10, 20, 50, 60, 30);
        CompositeComponent livestockArea = new CompositeComponent("Live-stock-area", 500, 15, 25, 30, 40, 20);
        LeafComponent cow = new LeafComponent("Cow", 200, 15, 25, 10, 10, 10);
        livestockArea.addComponent(cow);
        barn.addComponent(livestockArea);
        root.addComponent(barn);
        LeafComponent crop = new LeafComponent("Crop", 50, 30, 40, 5, 5, 5);
        root.addComponent(crop);
        return root;
    }
}
