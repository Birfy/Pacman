package object;

import javafx.scene.image.ImageView;
import main.Data;
import main.Main;

public abstract class MapObject extends Main implements Data {
    protected int x, y;
    protected int type;
    protected ImageView image;

    public void initiateImage() {
        image.setX(WIDTH_OF_NODES * x);
        image.setY(WIDTH_OF_NODES * y);
        image.setFitHeight(WIDTH_OF_NODES);
        image.setFitWidth(WIDTH_OF_NODES);
    }

    public int getType() {
        return type;
    }

    public ImageView getImage() {
        return image;
    }

    public abstract void eat();

    public abstract char getTypeChar();
}
