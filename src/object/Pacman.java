package object;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import main.Data;

import java.nio.file.Paths;

public class Pacman extends MovingObject implements Data {
    protected Timeline pacmanMoveConstantly;

    public Pacman(int y, int x) {
        super(y, x, "resource/pacman-00.gif");
    }

    public void moveUntilWall(int direction) {
        if (pacmanMoveConstantly != null && !(map[getNewPosition(direction)[1]][getNewPosition(direction)[0]] instanceof Wall))
            pacmanMoveConstantly.stop();
        if (pacmanMoveConstantly != null && (direction - isMove) % 2 == 0 && isMove >= 0)
            pacmanMoveConstantly.stop();

        if (!(map[getNewPosition(direction)[1]][getNewPosition(direction)[0]] instanceof Wall)) {
            final int finalDirection = direction;
            moveToDirection(finalDirection);
            pacmanMoveConstantly = new Timeline(new KeyFrame(Duration.millis(TIME_OF_MOVE), event -> moveToDirection(finalDirection)));

            if (getDistance(direction) == -1) {
                pacmanMoveConstantly.setCycleCount(Animation.INDEFINITE);
                pacmanMoveConstantly.play();
            } else if (getDistance(direction) != 0) {
                pacmanMoveConstantly.setCycleCount(getDistance(direction));
                pacmanMoveConstantly.play();
            }
        }
    }

    public void moveToDirection(int direction) {
        if (!(map[getNewPosition(direction)[1]][getNewPosition(direction)[0]] instanceof Wall)) {
            setImage(direction);
            move(direction);
            if (isWin()) {
                monster[0].getImage().requestFocus();
                for (int i = 0; i < monsterNum; i++) {
                    monster[i].getTimeline().stop();
                    monster[i].getImage().setVisible(false);
                    monster[i].setIsDead(false);
                }
                if (getMoveTimeline() != null)
                    getMoveTimeline().stop();
                if (getTimeline() != null)
                    getTimeline().stop();

                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> win()));
                Timeline timelineGettingBig = new Timeline(new KeyFrame(Duration.millis(100), e -> {
                    image.setX(image.getX() - 2);
                    image.setY(image.getY() - 2);
                    image.setFitWidth(image.getFitWidth() + 4);
                    image.setFitHeight(image.getFitHeight() + 4);
                }));
                timelineGettingBig.setCycleCount(10);
                timelineGettingBig.play();
                timeline.play();
            }
        }
    }

    public void setImage(int direction) {
        isMove = direction;
        image.setImage(new Image(Paths.get("resource/pacman-" + status + isMove + ".gif").toUri().toString()));
        image.setFitWidth(WIDTH_OF_NODES);
        image.setFitHeight(WIDTH_OF_NODES);
    }

    public Timeline getTimeline() {
        return pacmanMoveConstantly;
    }
}