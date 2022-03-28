package object;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import main.Data;

import java.nio.file.Paths;
import java.util.ArrayList;

public class Monster extends MovingObject implements Data {
    private int type;
    private Timeline ghostMove;
    private int index;
    private boolean isDead;
    private Timeline timelineDie;

    public Monster(int y, int x, int type, int index) {
        super(y, x, "resource/ghost" + type + "0.gif");
        this.type = type;
        this.index = index;
        isDead = false;
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    public boolean getIsDead() {
        return isDead;
    }

    public void moveMonster() {
        switch (type) {
            case 0:
                ghostMove = new Timeline(new KeyFrame(Duration.millis(TIME_OF_MOVE), e -> randomMoveToDirection()));
                break;
            case 1:
                ghostMove = new Timeline(new KeyFrame(Duration.millis(TIME_OF_MOVE), e -> moveDirectly()));
                break;
        }
        ghostMove.setCycleCount(Animation.INDEFINITE);
        ghostMove.play();
    }

    public void moveDirectly() {
        int direction = -1;

        if (isOnCross()) {
            int randomOrientation = (int) (Math.random() * 2);
            direction = getChaseDirection(randomOrientation);
            if (map[getNewPosition(direction)[1]][getNewPosition(direction)[0]] instanceof Wall)
                direction = getChaseDirection(1 - randomOrientation);
            if (map[getNewPosition(direction)[1]][getNewPosition(direction)[0]] instanceof Wall)
                while (map[getNewPosition(direction)[1]][getNewPosition(direction)[0]] instanceof Wall)
                    direction = (int) (Math.random() * 4);
        } else if (map[getNewPosition(isMove)[1]][getNewPosition(isMove)[0]] instanceof Wall) {
            while (map[getNewPosition(direction)[1]][getNewPosition(direction)[0]] instanceof Wall)
                direction = (int) (Math.random() * 4);
        } else {
            direction = isMove;
        }
        judgeAction();
        move(direction);
    }

    public int getChaseDirection(int orientation) {
        int direction = -1;
        switch (orientation) {
            case 0:
                if (x > pacman.getX())
                    direction = 3;
                else if (x < pacman.getX())
                    direction = 1;
                else {
                    if (y < pacman.getY())
                        direction = 2;
                    else
                        direction = 0;
                }
                break;
            case 1:
                if (y > pacman.getY())
                    direction = 0;
                else if (y < pacman.getY())
                    direction = 2;
                else {
                    if (x < pacman.getX())
                        direction = 1;
                    else
                        direction = 3;
                }
                break;
        }
        return direction;
    }

    public void randomMoveToDirection() {
        int direction = (int) (Math.random() * 4);
        if (!(map[getNewPosition(direction)[1]][getNewPosition(direction)[0]] instanceof Wall)) {
            judgeAction();
            move(direction);
        } else
            randomMoveToDirection();
    }

    public void setImage() {
        image.setImage(new Image(Paths.get("resource/ghost" + type + status + ".gif").toUri().toString()));
        image.setFitWidth(WIDTH_OF_NODES);
        image.setFitHeight(WIDTH_OF_NODES);
    }

    public boolean isOnCross() {
        int count = 0;
        ArrayList<Integer> directions = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            if (map[getNewPosition(i)[1]][getNewPosition(i)[0]] instanceof Bean) {
                count++;
                directions.add(i);
            }
        return count > 2 || (directions.size() == 2 && (directions.get(0) - directions.get(1)) % 2 != 0);
    }

    public Timeline getTimelineDie() {
        return timelineDie;
    }

    public Timeline getTimeline() {
        return ghostMove;
    }

    public void judgeAction() {
        if (image.intersects(pacman.getImage().getBoundsInParent()) && getStatus() == 0) {
            if (showStatus.getLife() <= 1) {
                monster[0].getImage().requestFocus();
                for (int i = 0; i < monsterNum; i++) {
                    monster[i].getTimeline().stop();
                    monster[i].getImage().setVisible(false);
                    monster[i].setIsDead(false);
                }
                if (pacman.getMoveTimeline() != null)
                    pacman.getMoveTimeline().stop();
                if (pacman.getTimeline() != null)
                    pacman.getTimeline().stop();
                pacman.setImage(-1);

                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> die()));
                timeline.play();
            } else
                recoverMap();
        } else if (image.intersects(pacman.getImage().getBoundsInParent()) && getStatus() == 1) {
            showStatus.addScore(SCORES_PER_MONSTER);
            dieForAWhile();
        }
    }

    public void dieForAWhile() {
        isDead = true;
        ghostMove.stop();
        getImage().setVisible(false);
        timelineDie = new Timeline(new KeyFrame(Duration.millis(5000), e -> {
            if (!getImage().isVisible() && isDead) {
                setX(monsterX[index]);
                setY(monsterY[index]);
                getImage().setX(monsterX[index] * WIDTH_OF_NODES);
                getImage().setY(monsterY[index] * WIDTH_OF_NODES);
                getImage().setVisible(true);
                moveMonster();
                isDead = false;
            }
        }));
        timelineDie.setCycleCount(1);
        timelineDie.play();
    }

    public int getType() {
        return type;
    }
}