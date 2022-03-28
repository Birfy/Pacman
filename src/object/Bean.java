package object;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Data;
import main.Sound;

import java.nio.file.Paths;

public class Bean extends MapObject implements Data {

    //Type 0-Null 1-NormalBean 2-BigBean
    public Bean(int y, int x, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
        image = new ImageView(new Image(Paths.get("resource/bean" + type + ".jpg").toUri().toString()));
        initiateImage();
    }

    public void setImage(String filename) {
        image.setImage(new Image(Paths.get(filename).toUri().toString()));
    }

    @Override
    public void eat() {
        switch (type) {
            case 1:
                Sound.eatBeanSound();
                showStatus.addScore(SCORES_PER_BEAN1);
                break;
            case 2:
                Sound.eatBeanSound();
                showStatus.addScore(SCORES_PER_BEAN2);
                pacman.bigStrengthMode();
                break;
        }
        type = 0;
        setImage("resource/bean0.jpg");
    }

    public char getTypeChar() {
        switch (type) {
            case 0: return 'n';
            case 1: return '.';
            case 2: return 'a';
            default: return ' ';
        }
    }
}