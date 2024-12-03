package farmmanagement.controllers;

import farmmanagement.models.CompositeComponent;
import farmmanagement.models.FarmComponent;
import farmmanagement.models.LeafComponent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddItemController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private ComboBox<String> itemContainerDropdown;
    @FXML
    private TextField positionXField;
    @FXML
    private TextField positionYField;
    @FXML
    private TextField lengthField;
    @FXML
    private TextField widthField;
    @FXML
    private TextField heightField;

    private CompositeComponent rootComponent;
    private CompositeComponent selectedComponent;
    private DashboardController dashboardController;

    public void setCompositeComponent(CompositeComponent root) {
        this.rootComponent = root;
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void handleAddItem() {
        if (itemContainerDropdown.getValue() != null) {
            String selectedContainerName = itemContainerDropdown.getValue();
            setSelectedComponent(selectedContainerName);
        }
        if (isInputValid()) {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int x = Integer.parseInt(positionXField.getText());
            int y = Integer.parseInt(positionYField.getText());
            int length = Integer.parseInt(lengthField.getText());
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());

            LeafComponent newLeaf = new LeafComponent(name, price, x, y, length, width, height);
            selectedComponent.addComponent(newLeaf);
            closeWindow();
            if (dashboardController != null) {
                dashboardController.refreshTree(rootComponent);
            }
        }
    }

    // HELPER METHODS
    private void setSelectedComponent(String selectedContainerName) {
        selectedComponent = (CompositeComponent) rootComponent.findComponentByName(selectedContainerName);
        System.out.print("Selected Dropdown Option: " + selectedComponent.getName());
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText().isEmpty()) {
            errorMessage += "Name cannot be empty.\n";
        }
        if (priceField.getText().isEmpty() || !isInteger(priceField.getText())) {
            errorMessage += "Price must be a valid number.\n";
        }
        if (positionXField.getText().isEmpty() || !isInteger(positionXField.getText()) || !isInsideComponent(positionXField.getText(), positionYField.getText(), heightField.getText(), widthField.getText())) {
            errorMessage += "Item you trying to add must be in the Selected Container Boundaries.\n";
        }
        if (positionYField.getText().isEmpty() || !isInteger(positionYField.getText()) || !isInsideComponent(positionXField.getText(), positionYField.getText(), heightField.getText(), widthField.getText())) {
            errorMessage += "Item you trying to add must be in the Selected Container Boundaries.\n";
        }
        if (lengthField.getText().isEmpty() || !isFloat(lengthField.getText()) || Float.parseFloat(lengthField.getText()) <= 0) {
            errorMessage += "Length must be a valid positive number.\n";
        }
        if (widthField.getText().isEmpty() || !isFloat(widthField.getText()) || Float.parseFloat(widthField.getText()) <= 0) {
            errorMessage += "Width must be a valid positive number.\n";
        }
        if (heightField.getText().isEmpty() || !isFloat(heightField.getText()) || Float.parseFloat(heightField.getText()) <= 0) {
            errorMessage += "Height must be a valid positive number.\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showErrorDialog("Invalid Input", "Please fix the following errors:", errorMessage);
            return false;
        }
    }

    private boolean isInteger(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isFloat(String text) {
        try {
            Float.parseFloat(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isInsideComponent(String x, String y, String h, String w) {
        int positionX = Integer.parseInt(x);
        int positionY = Integer.parseInt(y);
        int width = Integer.parseInt(w);
        int height = Integer.parseInt(h);

        int[] containerPosition = selectedComponent.getPosition();
        int[] containerDimensions = selectedComponent.getDimensions();
        if (selectedComponent.getName().equals("Root")) {
            return true;
        }
        if (positionX == containerPosition[0] && positionY == containerPosition[1] && width <= containerDimensions[1] && height <= containerDimensions[2]) {
            return true;
        } else {
            return false;
        }
    }

    private void showErrorDialog(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void populateItemContainerDropdown() {
        itemContainerDropdown.getItems().clear(); // Clear existing items

        if (rootComponent != null) {
            for (FarmComponent component : rootComponent.getAllNodes()) {
                System.out.println("Adding to ComboBox: " + component.getName());
                if (component instanceof CompositeComponent) {
                    itemContainerDropdown.getItems().add(component.getName());
                }
            }
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
