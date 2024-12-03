package farmmanagement.controllers;

import farmmanagement.models.CompositeComponent;
import farmmanagement.models.FarmComponent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditItemContainerController implements Initializable {

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

    private FarmComponent toEditComponent;
    private CompositeComponent rootComponent;
    private CompositeComponent selectedComponent;
    private DashboardController dashboardController;

    public void setCompositeComponent(CompositeComponent root) {
        this.rootComponent = root;
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public void setFarmComponent(FarmComponent toEditComponent) {
        this.toEditComponent = toEditComponent;
        nameField.setText(toEditComponent.getName());
        priceField.setText(String.valueOf(toEditComponent.getPrice()));
        positionXField.setText(String.valueOf(toEditComponent.getPosition()[0]));
        positionYField.setText(String.valueOf(toEditComponent.getPosition()[1]));
        lengthField.setText(String.valueOf(toEditComponent.getDimensions()[0]));
        widthField.setText(String.valueOf(toEditComponent.getDimensions()[1]));
        heightField.setText(String.valueOf(toEditComponent.getDimensions()[2]));
    }

    @FXML
    private void handleUpdateItemContainer() {
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

            toEditComponent.setName(name);
            toEditComponent.setPrice(price);
            toEditComponent.setPosition(x, y);
            toEditComponent.setDimensions(length, width, height);

            for (FarmComponent component : rootComponent.getAllNodes()) {
                if (component instanceof CompositeComponent composite) {
                    itemContainerDropdown.getItems().add(composite.getName());
                    if (composite.getChildren().contains(toEditComponent)) {
                        String selectedContainerName = composite.getName();
                        composite.removeComponent(toEditComponent);
                    }
                }
            }

            selectedComponent.addComponent(toEditComponent);

            closeWindow();

            if (dashboardController != null) {
                dashboardController.refreshTree(rootComponent);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    // HELPER METHODS
    private void setSelectedComponent(String selectedContainerName) {
        selectedComponent = rootComponent.findComponentByName(selectedContainerName);
        System.out.print("Selected Dropdown Option: " + selectedComponent.getName());
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText().isEmpty()) {
            errorMessage += "Name cannot be empty.\n";
        }
        if (priceField.getText().isEmpty() || !isDouble(priceField.getText())) {
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

    private boolean isDouble(String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
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
        if (rootComponent != null) {
            itemContainerDropdown.getItems().clear(); // Clear existing items
            String selectedContainerName = null; // Variable to hold the container name

            for (FarmComponent component : rootComponent.getAllNodes()) {
                if (component instanceof CompositeComponent composite) {
                    itemContainerDropdown.getItems().add(composite.getName());
                    if (composite.getChildren().contains(toEditComponent)) {
                        selectedContainerName = composite.getName();
                    }
                }
            }

            // Set the selected item in the ComboBox
            if (selectedContainerName != null) {
                itemContainerDropdown.setValue(selectedContainerName);
            }

        }
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
