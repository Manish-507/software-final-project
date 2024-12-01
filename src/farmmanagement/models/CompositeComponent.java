package farmmanagement.models;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TreeItem;

public class CompositeComponent extends AbstractFarmComponent {

    private List<FarmComponent> children = new ArrayList<>();

    public CompositeComponent(String name, double price, int positionX, int positionY, int length, int width, int height) {
        super(name, price, positionX, positionY, length, width, height);
    }

    public void addComponent(FarmComponent component) {
        children.add(component);
    }

    public void removeComponent(FarmComponent component) {
        children.remove(component);
    }

    public List<FarmComponent> getChildren() {
        return children;
    }

    @Override
    public void display() {
        System.out.println("Composite: " + name + " [Price: " + price + ", Position: (" + positionX + "," + positionY + "), Dimensions: " + length + "x" + width + "x" + height + "]");
        for (FarmComponent child : children) {
            child.display();
        }
    }

    @Override
    public void destroy() {
        System.out.println("Destroying Composite: " + name);
        for (FarmComponent child : children) {
            child.destroy();
        }
        children.clear();
    }

    // Convert to TreeItem for TreeView
    public TreeItem<String> toTreeItem() {
        TreeItem<String> rootItem = new TreeItem<>(this.getName());
        for (FarmComponent child : children) {
            rootItem.getChildren().add(child.toTreeItem());
        }
        return rootItem;
    }
}
