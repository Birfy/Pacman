package object;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import main.Data;
import main.Main;

import java.nio.file.Paths;

public abstract class MovingObject extends Main implements Data {
    protected int x, y;
    protected int status;
    protected ImageView image;
    protected int isMove;
    protected Timeline moveTimeline;
    protected Timeline bigStrengthTime;

    protected MovingObject(int y, int x, String filename) {
        status = 0;
        this.x = x;
        this.y = y;

        image = new ImageView(new Image(Paths.get(filename).toUri().toString()));
        image.setX(WIDTH_OF_NODES * x);
        image.setY(WIDTH_OF_NODES * y);
        image.setFitWidth(WIDTH_OF_NODES);
        image.setFitHeight(WIDTH_OF_NODES);
        isMove = -1;
    }

    public int getIsMove() {
        return isMove;
    }

    public void setIsMove(int isMove) {
        this.isMove = isMove;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Timeline getMoveTimeline() {
        return moveTimeline;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ImageView getImage() {
        return image;
    }

    public int[] getNewPosition(int direction) {
        int[] newPosition = new int[2];
        switch (direction) {
            case 0:
                newPosition[0] = x;
                newPosition[1] = (y - 1 + COLUMN_SIZE) % COLUMN_SIZE;
                break;
            case 1:
                newPosition[0] = (x + 1 + ROW_SIZE) % ROW_SIZE;
                newPosition[1] = y;
                break;
            case 2:
                newPosition[0] = x;
                newPosition[1] = (y + 1 + COLUMN_SIZE) % COLUMN_SIZE;
                break;
            case 3:
                newPosition[0] = (x - 1 + ROW_SIZE) % ROW_SIZE;
                newPosition[1] = y;
                break;
        }
        return newPosition;
    }

    public void move(int direction) {
        EventHandler<ActionEvent> moveEvent = e -> {
            switch (direction) {
                case 0:
                    isMove = 0;
                    image.setY((image.getY() - PIXEL_PER_MOVE + WIDTH_OF_NODES * COLUMN_SIZE) % (WIDTH_OF_NODES * COLUMN_SIZE));
                    break;
                case 1:
                    isMove = 1;
                    image.setX((image.getX() + PIXEL_PER_MOVE + WIDTH_OF_NODES * ROW_SIZE) % (WIDTH_OF_NODES * ROW_SIZE));
                    break;
                case 2:
                    isMove = 2;
                    image.setY((image.getY() + PIXEL_PER_MOVE + WIDTH_OF_NODES * COLUMN_SIZE) % (WIDTH_OF_NODES * COLUMN_SIZE));
                    break;
                case 3:
                    isMove = 3;
                    image.setX((image.getX() - PIXEL_PER_MOVE + WIDTH_OF_NODES * ROW_SIZE) % (WIDTH_OF_NODES * ROW_SIZE));
                    break;
            }
        };

        moveTimeline = new Timeline(new KeyFrame(Duration.millis(MILLISECOND_REFRESH), moveEvent));
        moveTimeline.setCycleCount(REFRESH_COUNT);
        moveTimeline.play();

        setX(getNewPosition(direction)[0]);
        setY(getNewPosition(direction)[1]);

        if (this instanceof Pacman)
            if (map[getY()][getX()] instanceof Bean)
                map[getY()][getX()].eat();
    }

    public int getDistance(int direction) {
        int distance = 0;
        switch (direction) {
            case 0:
                for (int i = getY(); i > getY() - COLUMN_SIZE; i--) {
                    if (map[(i + COLUMN_SIZE) % COLUMN_SIZE][getX()] instanceof Wall) {
                        distance = getY() - i - 1;
                        break;
                    }
                    distance = -1;
                }
                break;
            case 1:
                for (int i = getX(); i < getX() + ROW_SIZE; i++) {
                    if (map[getY()][i % ROW_SIZE] instanceof Wall) {
                        distance = i - getX() - 1;
                        break;
                    }
                    distance = -1;
                }
                break;
            case 2:
                for (int i = getY(); i < getY() + COLUMN_SIZE; i++) {
                    if (map[i % COLUMN_SIZE][getX()] instanceof Wall) {
                        distance = i - getY() - 1;
                        break;
                    }
                    distance = -1;
                }
                break;
            case 3:
                for (int i = getX(); i > getX() - ROW_SIZE; i--) {
                    if (map[getY()][(i + ROW_SIZE) % ROW_SIZE] instanceof Wall) {
                        distance = getX() - i - 1;
                        break;
                    }
                    distance = -1;
                }
                break;
        }
        return distance;
    }

    public void bigStrengthMode() {
        pacman.setStatus(2);
        pacman.setImage(isMove);
        for (int i = 0; i < monsterNum; i++) {
            monster[i].setStatus(1);
            monster[i].setImage();
        }

        if (bigStrengthTime != null)
            bigStrengthTime.stop();

        bigStrengthTime = new Timeline(new KeyFrame(Duration.millis(10000), e -> {
            pacman.setStatus(0);
            pacman.setImage(isMove);
            for (int i = 0; i < monsterNum; i++) {
                monster[i].setStatus(0);
                monster[i].setImage();
            }
        }));
        bigStrengthTime.setCycleCount(1);
        bigStrengthTime.play();
    }

    public Timeline getBigStrengthTime() {
        return bigStrengthTime;
    }

    abstract Timeline getTimeline();
}