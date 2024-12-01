package farmmanagement.models;

import javafx.scene.control.TreeItem;

public abstract class AbstractFarmComponent implements FarmComponent {

    protected String name;
    protected double price;
    protected int positionX;
    protected int positionY;
    protected int length;
    protected int width;
    protected int height;

    public AbstractFarmComponent(String name, double price, int positionX, int positionY, int length, int width, int height) {
        this.name = name;
        this.price = price;
        this.positionX = positionX;
        this.positionY = positionY;
        this.length = length;
        this.width = width;
        this.height = height;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPosition(int x, int y) {
        this.positionX = x;
        this.positionY = y;
    }

    @Override
    public int[] getPosition() {
        return new int[]{positionX, positionY};
    }

    @Override
    public void setDimensions(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    @Override
    public int[] getDimensions() {
        return new int[]{length, width, height};
    }
}
