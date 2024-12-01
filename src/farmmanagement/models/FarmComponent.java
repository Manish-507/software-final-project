package farmmanagement.models;

import javafx.scene.control.TreeItem;

public interface FarmComponent {

    void display();

    void destroy();

    public abstract TreeItem<String> toTreeItem();

    // Common property methods
    String getName();

    double getPrice();

    void setPosition(int x, int y);

    int[] getPosition();

    void setDimensions(int length, int width, int height);

    int[] getDimensions();
}
