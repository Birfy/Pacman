package object;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Data;

import java.nio.file.Paths;

public class Wall extends MapObject implements Data {

    public Wall(int y, int x, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
        image = new ImageView(new Image(Paths.get("resource/map" + type + ".jpg").toUri().toString()));
        initiateImage();
    }

    @Override
    public void eat() {
    }

    public char getTypeChar() {
        return (char)(type + '0');
    }
}