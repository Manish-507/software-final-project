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

    public List<FarmComponent> getAllNodes() {
        List<FarmComponent> nodes = new ArrayList<>();
        nodes.add(this);
        // Recursively add all children nodes
        for (FarmComponent child : children) {
            if (child instanceof CompositeComponent) {
                nodes.addAll(((CompositeComponent) child).getAllNodes());
            }
        }
        return nodes;
    }

    public FarmComponent findComponentByName(String name) {
        if (this.getName().equals(name)) {
            return this;  // Return the current component
        }

        // Recursively search through children
        for (FarmComponent child : this.getChildren()) {
            if (child instanceof CompositeComponent) {
                FarmComponent found = ((CompositeComponent) child).findComponentByName(name);
                if (found != null) {
                    return found;
                }
            } else if (child instanceof LeafComponent) {
                if (child.getName().equals(name)) {
                    return child;
                }
            }
        }
        return null;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }
}
