package farmmanagement.models;

import javafx.scene.control.TreeItem;

public class LeafComponent extends AbstractFarmComponent {

    public LeafComponent(String name, double price, int positionX, int positionY, int length, int width, int height) {
        super(name, price, positionX, positionY, length, width, height);
    }

    @Override
    public void display() {
        System.out.println("Leaf: " + name + " [Price: " + price + ", Position: (" + positionX + "," + positionY + "), Dimensions: " + length + "x" + width + "x" + height + "]");
    }

    @Override
    public void destroy() {
        System.out.println("Destroying Leaf: " + name);
    }

    // Convert to TreeItem for TreeView
    public TreeItem<String> toTreeItem() {
        return new TreeItem<>(this.getName());
    }
}
