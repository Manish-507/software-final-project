package farmmanagement.controllers;

import farmmanagement.models.CompositeComponent;
import farmmanagement.models.FarmComponent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddItemContainerController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private ComboBox itemContainerDropdown;
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
    private DashboardController dashboardController;

    public void setCompositeComponent(CompositeComponent root) {
        this.rootComponent = root;
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateItemContainerDropdown();
    }

    // HELPER METHODS
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

}
